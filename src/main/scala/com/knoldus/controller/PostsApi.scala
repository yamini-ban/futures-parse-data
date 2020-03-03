package com.knoldus.controller

import com.knoldus.model.{Comments, PostWithComments, Posts}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostsApi(posts: Future[List[Posts]], commentApi: CommentApi) {

  def getPosts: Future[List[Posts]] = {
    posts
  }

  def getPostWithMaxCommentCount: Future[(Long, Int)] = {
    def innerGetPostWithMaxCommentCount(listOfPostWithCommentCount: List[(Long, Int)]) = {
      listOfPostWithCommentCount.foldLeft(-1.toLong, -1.toInt)((post1, post2) => {
        (post1, post2) match {
          case ((post1Id, count1), (_, count2)) if count1 > count2 => (post1Id, count1)
          case (_, post2) => post2
        }
      })
    }

    for {
      listOfPostWithCommentCount <- getListOfPostsWithCommentCount
    } yield innerGetPostWithMaxCommentCount(listOfPostWithCommentCount)
  }

  private def getListOfPostsWithCommentCount: Future[List[(Long, Int)]] = {
    def innerGetListOfPostsWithCommentsCount(listOfPostsWithCommentIds: List[PostWithComments]) = {
      listOfPostsWithCommentIds.map(postWithComment => {
        (postWithComment.postId, postWithComment.comments.length)
      })
    }

    for (listOfPostsWithCommentIds <- getListOfPostsWithCommentIds)
      yield innerGetListOfPostsWithCommentsCount(listOfPostsWithCommentIds)
  }

  private def getListOfPostsWithCommentIds: Future[List[PostWithComments]] = {
    def innerGetListOfPostsWithCommentIds(listOfPosts: List[Posts], listOfComments: List[Comments]) = {
      listOfPosts.map(post => {
        PostWithComments(post.id, listOfComments.filter(_.postId == post.id).map(_.id))
      })
    }

    for {
      listOfPosts <- posts
      listOfComments <- commentApi.getComments
    } yield innerGetListOfPostsWithCommentIds(listOfPosts, listOfComments)

  }

  def getPostWithPostId(listOfPosts: List[Posts], id: Long): Option[Posts] = {
    listOfPosts match {
      case post :: _ if post.id == id => Some(post)
      case _ :: remainingListOfPosts => getPostWithPostId(remainingListOfPosts, id)
      case Nil => None
    }
  }

}
