package com.knoldus.controller

import com.knoldus.model.{CustomException, Posts, UserWithPosts, Users}
import com.knoldus.{PostsAPI, UserAPI}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserApi(val url: String) extends ParseData[Users] {

  def getListOfParsedUsers: Future[List[Users]] = {
    parseData(url)
  }

  def getUserWithMaximumPostsCount: Future[(Long, Int)] = {
    def innerGetUserWithMaximumPostsCount(listOfUsersWithPostCount: List[(Long, Int)]): (Long, Int) = {
      listOfUsersWithPostCount.foldLeft(-1.toLong, -1.toInt)((user1, user2) => {
        (user1, user2) match {
          case ((u1, count1), (_, count2)) if count1 > count2 => (u1, count1)
          case (_, user2WithCount) => user2WithCount
        }
      })
    }

    for {
      listOfUsersWithPostCount <- getListOfUsersWithPostCount
    } yield innerGetUserWithMaximumPostsCount(listOfUsersWithPostCount)
  }

  def getListOfUsersWithPostCount: Future[List[(Long, Int)]] = {
    def innerGetListOfUserWithPostIds(listOfUsersWithPostIds: List[UserWithPosts]): List[(Long, Int)] = {
      listOfUsersWithPostIds.map(oneUserWithAllPostIds => (oneUserWithAllPostIds.user, oneUserWithAllPostIds.posts.length))
    }

    for {
      listOfUsersWithPostIds <- getListOfUsersWithPostIds
    } yield innerGetListOfUserWithPostIds(listOfUsersWithPostIds)

  }

  def getListOfUsersWithPostIds: Future[List[UserWithPosts]] = {
    def innerGetListOfUsersWithPostIds(listOfUsers: List[Users], listOfPosts: List[Posts]): List[UserWithPosts] = {
      listOfUsers.map(user => {
        UserWithPosts(user.id, listOfPosts.filter(_.userId == user.id).map(_.id))
      })
    }

    for {
      listOfUsers <- UserAPI.users
      listOfPosts <- PostsAPI.posts
    } yield innerGetListOfUsersWithPostIds(listOfUsers, listOfPosts)
  }

  def getUserWithUserId(listOfUsers: List[Users], id: Long): Option[Users] = {
    listOfUsers match {
      case user :: _ if user.id == id => Some(user)
      case _ :: remainingListOfUsers => getUserWithUserId(remainingListOfUsers, id)
      case Nil => None
    }
  }

  def getUserWithMaxPostComments: Future[(String, Long, Int)] = {
    for {
      listOfUsers <- UserAPI.users
      listOfPosts <- PostsAPI.posts
      postWithMaxCommentCount <- PostsAPI.getPostWithMaxCommentCount
    } yield (getUserWithGivenPostId(listOfUsers, listOfPosts, postWithMaxCommentCount._1).name,
      postWithMaxCommentCount._1, postWithMaxCommentCount._2)
  }

  private def getUserWithGivenPostId(listOfUsers: List[Users], listOfPosts: List[Posts], postId: Long): Users = {
    val postWithGivenId = PostsAPI.getPostWithPostId(listOfPosts, postId) match {
      case Some(post) => post
      case None => Posts(-1, -1, "", "")
    }
    val userWithGivenUserId = getUserWithUserId(listOfUsers, postWithGivenId.userId)
    userWithGivenUserId match {
      case Some(user) => user
      case None => throw new CustomException("Something went wrong...")
    }
  }

}
