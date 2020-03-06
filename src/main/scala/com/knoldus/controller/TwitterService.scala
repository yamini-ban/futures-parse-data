package com.knoldus.controller

import twitter4j._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

trait TwitterService {
  def getData(tag: String, twitterInstance: TwitterInstance): Future[List[Status]]
}

object TwitterService extends TwitterService {
  def getData(tag: String, twitterInstance: TwitterInstance): Future[List[Status]] = {
    val query = new Query(tag)
    val data = Future(twitterInstance.getTwitterInstance.search(query).getTweets.asScala.toList)
    data.recover({
      case e: Exception => List.empty[Status]
    })
  }
}
