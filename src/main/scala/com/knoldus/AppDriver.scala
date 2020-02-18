package com.knoldus

import com.knoldus.controller.{CommentApi, PostsApi, UserApi}
import com.knoldus.model.{PostWithComments, UserWithPosts}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object AppDriver extends App{

  object userAPI extends UserApi("https://jsonplaceholder.typicode.com/users")
  val users = Future{
    userAPI.getListOfParsedUsers
  }

  object postAPI extends PostsApi("https://jsonplaceholder.typicode.com/posts")
  val posts = Future {
    postAPI.getListOfParsedPosts
  }

  object commentAPI extends CommentApi("https://jsonplaceholder.typicode.com/comments")
  val comments = Future {
    commentAPI.getListOfParsedComments
  }

  val userWithPosts = users.map(listOfUsers => {
    val result = listOfUsers.map(user => {
      posts.map(listOfPosts => {
        UserWithPosts(user.id,listOfPosts.filter(_.userId == user.id).map(_.id))
      })
    })
    Future.sequence(result)
  }).flatten

  val postWithComments = posts.map(listOfPosts =>{
    val result = listOfPosts.map(post => {
      comments.map(listOfComments => PostWithComments(post.id, listOfComments.filter(_.postId == post.id).map(_.id)))
    })
    Future.sequence(result)
  }).flatten

  val listOfUsersWithPostCount = userWithPosts.map(
    _.map(userWithPost => (userWithPost.user, userWithPost.posts.length))
  )

  val userWithMaximumPosts = listOfUsersWithPostCount.map(_.foldLeft(-1.toLong,-1.toInt)((user1, user2) => {
    (user1, user2) match {
      case ((u1, count1), (_, count2)) if count1 > count2 => (u1, count1)
      case (_, user2WithCount) => user2WithCount
    }
  })
  )

  val listOfPostsWithCommentsCount = postWithComments.map( _.map(postWithComment => {
    (postWithComment.postId, postWithComment.comments.length)
  })
  )


  val postWithMaxComments = listOfPostsWithCommentsCount.map(
    _.foldLeft(-1.toLong, -1.toInt)((post1, post2) => {
      (post1, post2) match {
        case ((post1WithCount, count1), (_, count2)) if count1 > count2 => (post1WithCount, count1)
        case (_, post2WithCount) => post2WithCount
      }
    })
  )
//cannot use filter use any method may be for..
  val userWithPostAndMaxCount = (
    users.map(listOfUsers => {
      listOfUsers.find(
        user => {
          val id = Future(user.id)
          id == posts.map(listOfPosts => {
            listOfPosts.find(
              post => {
                val postId = Future(post.id)
                postId == postWithMaxComments.map(_._1)
              }
            ).get.userId
          })
        }
      )
    }), postWithMaxComments.map(_._1), postWithMaxComments.map(_._2))

  Thread.sleep(15 * 1000)
  println(userWithPostAndMaxCount)

}
