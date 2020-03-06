package com.knoldus

import twitter4j._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

trait TwitterService {
  def getData(tag: String): Future[List[Status]]
}

object TwitterService {
  def getData(tag: String, twitterInstance: TwitterInstance): Future[List[Status]] = {
    val query = new Query(tag)
    val twitterData = twitterInstance.getTwitterInstance
    val data = Future(twitterData.search(query).getTweets.asScala.toList)
    data.recover({
      case e: Exception => List.empty[Status]
    })
  }
}
