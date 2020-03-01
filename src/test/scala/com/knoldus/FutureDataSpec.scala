package com.knoldus

import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class FutureDataSpec extends AsyncFlatSpec{
 "getUserWithMaximumPostsCount " should "eventually return name of user with maximum posts." in {
   val futureUsername: Future[String] = FutureData.usernameWithMaxCommentCount
   futureUsername.map(find=> assert(find == "Clementina DuBuque"))
 }
  "getUserWithMaxPostComments " should "eventually return id of user with maximum comments on a post." in {
    val futureUserId: Future[Long] = FutureData.userWithMaxPostCount
    futureUserId.map(find=> assert(find == 10))
  }

}
