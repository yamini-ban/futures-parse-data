package com.knoldus

import com.knoldus.controller.{CommentApi, PostsApi, UserApi}
import com.knoldus.model.{Comments, Posts, Users}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UserAPI extends UserApi("https://jsonplaceholder.typicode.com/users") {
  val users: Future[List[Users]] = Future {
    UserAPI.getListOfParsedUsers
  }
}

object PostsAPI extends PostsApi("https://jsonplaceholder.typicode.com/posts") {
  val posts: Future[List[Posts]] = Future {
    PostsAPI.getListOfParsedPosts
  }
}

object CommentAPI extends CommentApi("https://jsonplaceholder.typicode.com/comments") {
  val comments: Future[List[Comments]] = Future {
    CommentAPI.getListOfParsedComments
  }
}

object AppDriver extends App{

  val postWithMaxComments: Future[(Long, Int)] = PostsAPI.getPostWithMaxCommentCount
  val userWithPostAndMaxCount: Future[(String, Long, Int)] = UserAPI.getUserWithMaxPostComments
  Thread.sleep(30 * 1000)
  println(postWithMaxComments + "\n")
  println(userWithPostAndMaxCount)

}
