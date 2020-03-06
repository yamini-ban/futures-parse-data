package com.knoldus.controller

import java.text.SimpleDateFormat

import com.knoldus.model
import com.knoldus.model.Status
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class TwitterControllerSpec extends AsyncFlatSpec with BeforeAndAfterAll{

  var twitterController: TwitterController[Status] = _
  var listOfTweets: List[Status] = _
  var formatter: SimpleDateFormat = _
  var emptyTwitterController: TwitterController[Status] = _

  override protected def beforeAll(): Unit = {
    emptyTwitterController = new TwitterController[Status](Future(List.empty[Status]))
    import java.text.SimpleDateFormat
    formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy")
    listOfTweets = List(model.Status(formatter.parse("Fri Mar 06 04:38:00 IST 2020"),0,0)
      , Status(formatter.parse("Fri Mar 06 03:48:38 IST 2020"),0,0)
      , Status(formatter.parse("Thu Mar 05 04:38:19 IST 2020"),0,0)
      , Status(formatter.parse("Wed Mar 04 20:00:00 IST 2020"),3,0)
      , Status(formatter.parse("Wed Mar 04 17:54:59 IST 2020"),0,4)
      , Status(formatter.parse("Wed Mar 04 17:21:35 IST 2020"),0,4)
      , Status(formatter.parse("Wed Mar 04 17:17:52 IST 2020"),10,4)
      , Status(formatter.parse("Wed Mar 04 16:37:59 IST 2020"),0,0)
      , Status(formatter.parse("Wed Mar 04 12:45:00 IST 2020"),6,0)
      , Status(formatter.parse("Wed Mar 04 04:37:57 IST 2020"),0,0)
      , Status(formatter.parse("Tue Mar 03 18:53:25 IST 2020"),0,0)
      , Status(formatter.parse("Tue Mar 03 17:08:44 IST 2020"),0,0)
      , Status(formatter.parse("Tue Mar 03 08:38:25 IST 2020"),0,0)
      , Status(formatter.parse("Mon Mar 02 16:37:59 IST 2020"),0,0)
      , Status(formatter.parse("Mon Mar 02 04:37:57 IST 2020"),0,0))
    twitterController = new TwitterController[Status](Future(listOfTweets))
  }

  "getTweetCount" should "eventually return count of Tweets fetched." in {
    val expectedResult = listOfTweets.length
    val actualResult = twitterController.getTweetCount
    for(actual <- actualResult) yield assert(actual == expectedResult)
  }

  "getAverageTweetsPerDay" should "eventually return average number of tweets per day." in {
    val expectedResult = 3
    val actualResult = twitterController.getAverageTweetsPerDay
    for(actual <- actualResult) yield assert(actual == expectedResult)
  }

  "getAverageTweetsPerDay" should "eventually throw an exception and recover with -1" +
    " in case of empty list of tweets." in {
    val actualResult = (emptyTwitterController.getAverageTweetsPerDay)
    val expectedResult = -1
    actualResult.map(actual => assert(actual == expectedResult))

  }

  "getAverageLikesPerTweet" should "eventually return average number of likes per tweet." in {
    val expectedResult = -1
    val actualResult = emptyTwitterController.getAverageLikesPerTweet
    for(actual <- actualResult) yield assert(actual == expectedResult)
  }

  "getAverageLikesPerTweet" should "eventually return -1 in case of empty list of tweet." in {
    val expectedResult = 1
    val actualResult = twitterController.getAverageLikesPerTweet
    for(actual <- actualResult) yield assert(actual == expectedResult)
  }

  "getAverageReTweetsPerTweet" should "eventually return average number of ReTweets per tweet." in {
      val expectedResult = 0
      val actualResult = twitterController.getAverageReTweetsPerTweet
      for(actual <- actualResult) yield assert(actual == expectedResult)
  }

  "getAverageReTweetsPerTweet" should "eventually return -1 in case of empty list of tweet." in {
      val expectedResult = -1
      val actualResult = emptyTwitterController.getAverageReTweetsPerTweet
      for(actual <- actualResult) yield assert(actual == expectedResult)
  }

}
