package com.knoldus

import com.knoldus.controller.{CommentApi, GetDataFromUrl, ParseData, PostsApi, UserApi}
import com.knoldus.model.{Comments, Posts, Users}

object AppDriver extends App {
  val sleepTime = 10*1000
  val comment = new CommentApi(ParseData.parseData[Comments]("Constants.commentUrl", GetDataFromUrl))
  val post = new PostsApi(ParseData.parseData[Posts](Constants.postUrl, GetDataFromUrl), comment)
  val user = new UserApi(ParseData.parseData[Users](Constants.userUrl, GetDataFromUrl), post )
  val c = user.getUserWithMaximumPostsCount

  Thread.sleep(sleepTime)
//  println(x)
  println(comment.getComments)

}
