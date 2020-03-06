package com.knoldus.controller

import java.time.{Period, ZoneId}
import java.util.Date

import com.knoldus.model.CustomException

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TwitterController[A <: {def getCreatedAt: Date; def getFavoriteCount: Int; def getRetweetCount: Int}](listOfTweets: Future[List[A]]) {

  def getTweetCount: Future[Int] = {
    val count = for (tweets <- listOfTweets) yield tweets.length
    count.recover({
      case e: Exception => -1
    })
  }

  def getAverageTweetsPerDay: Future[Int] = {
    val sortedListOfTweetsByDate = listOfTweets.map(list =>
      list.sortWith((tweet1, tweet2) => tweet1.getCreatedAt.before(tweet2.getCreatedAt)))

    val latestTweet = sortedListOfTweetsByDate.collect(list => list.headOption)
    val oldestTweet = sortedListOfTweetsByDate.collect(list => list.reverse.headOption)

    val duration = for {
      latestTweet <- latestTweet
      oldestTweet <- oldestTweet
    } yield {
      (latestTweet, oldestTweet) match {
        case (Some(latest), Some(oldest)) => val period = Period
          .between(latest.getCreatedAt.toInstant.atZone(ZoneId.systemDefault()).toLocalDate
            , oldest.getCreatedAt.toInstant.atZone(ZoneId.systemDefault()).toLocalDate)
          period.getDays
        case (_, _) => throw new CustomException("Tweets not found");
      }
    }

    val average = for {
      tweets <- listOfTweets
      duration <- duration
    } yield tweets.length / (duration + 1)

    average.recover({
      case e: Exception => -1
    })
  }

  def getAverageLikesPerTweet: Future[Int] = {
    val totalLikes = listOfTweets
      .map(list => list.foldLeft(0)((totalLikes, tweet) => totalLikes + tweet.getFavoriteCount))
    val average = for {
      list <- listOfTweets
      totalLikes <- totalLikes
    } yield totalLikes / list.length

    average.recover({
      case e: Exception => -1
    })
  }

  def getAverageReTweetsPerTweet: Future[Int] = {
    val totalReTweets = listOfTweets
      .map(list => list.foldLeft(0)((totalReTweets, tweet) => totalReTweets + tweet.getRetweetCount))
    val average = for {
      list <- listOfTweets
      totalReTweets <- totalReTweets
    } yield totalReTweets / list.length

    average.recover({
      case e: Exception => -1
    })
  }
}
