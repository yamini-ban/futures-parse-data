package com.knoldus

import com.knoldus.controller.{CommentApi, ParseData, PostsApi, UserApi}
import com.knoldus.model.{Comments, Posts, Users}

object AppDriver extends App {
  val sleepTime = 10*1000
  val comment = new CommentApi(ParseData.parseData[Comments](Constants.commentUrl))
  val post = new PostsApi(ParseData.parseData[Posts](Constants.postUrl), comment)
  val user = new UserApi(ParseData.parseData[Users](Constants.userUrl), post )
  val c = user.getUserWithMaximumPostsCount//ParseData.parseData[Posts]("https://jsonplaceholder.typicode.com/users")
  Thread.sleep(sleepTime)
  println(c)

}
