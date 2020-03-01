package com.knoldus

import com.knoldus.controller.{CommentApi, PostsApi, UserApi}
import com.knoldus.model.{Comments, Posts, Users}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UserAPI extends UserApi("https://jsonplaceholder.typicode.com/users") {
  val users: Future[List[Users]] = UserAPI.getListOfParsedUsers
}

object PostsAPI extends PostsApi("https://jsonplaceholder.typicode.com/posts") {
  val posts: Future[List[Posts]] = PostsAPI.getListOfParsedPosts
}

object CommentAPI extends CommentApi("https://jsonplaceholder.typicode.com/comments") {
  val comments: Future[List[Comments]] = {
    CommentAPI.getListOfParsedComments
  }
}

object FutureData {

  val userWithMaxPostCount: Future[Long] = UserAPI.getUserWithMaximumPostsCount.map(_._1)
  val usernameWithMaxCommentCount: Future[String] = UserAPI.getUserWithMaxPostComments.map(_._1)
}
