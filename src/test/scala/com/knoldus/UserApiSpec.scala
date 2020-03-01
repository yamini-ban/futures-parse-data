package com.knoldus

import com.knoldus.model.UserWithPosts
import org.scalatest.flatspec.AsyncFlatSpec

class UserApiSpec extends AsyncFlatSpec {

  "getUserWithMaximumPostsCount " should "eventually return id of user with posts count." in {
    val expectedResult = (10,10)
    val obtainedResult = UserAPI.getUserWithMaximumPostsCount
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

  "getListOfUsersWithPostIds " should "eventually return list of id of user with post ids." in {
    val expectedResult: List[UserWithPosts] = List(UserWithPosts(1,List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
      , UserWithPosts(2,List(11, 12, 13, 14, 15, 16, 17, 18, 19, 20))
      , UserWithPosts(3,List(21, 22, 23, 24, 25, 26, 27, 28, 29, 30))
      , UserWithPosts(4,List(31, 32, 33, 34, 35, 36, 37, 38, 39, 40))
      , UserWithPosts(5,List(41, 42, 43, 44, 45, 46, 47, 48, 49, 50))
      , UserWithPosts(6,List(51, 52, 53, 54, 55, 56, 57, 58, 59, 60))
      , UserWithPosts(7,List(61, 62, 63, 64, 65, 66, 67, 68, 69, 70))
      , UserWithPosts(8,List(71, 72, 73, 74, 75, 76, 77, 78, 79, 80))
      , UserWithPosts(9,List(81, 82, 83, 84, 85, 86, 87, 88, 89, 90))
      , UserWithPosts(10,List(91, 92, 93, 94, 95, 96, 97, 98, 99, 100))
    )
    val obtainedResult = UserAPI.getListOfUsersWithPostIds
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

  "getUserWithUserId " should "eventually return user with given userId." in {
    val expectedResult =  "Clementina DuBuque"
    val obtainedResult = for(listOfUsers <- UserAPI.users) yield UserAPI.getUserWithUserId(listOfUsers, 10)
    val obResult = obtainedResult.map {
      case Some(value) => value.name
      case None => ""
    }
    obResult.map(obtained => assert(expectedResult == obtained))
  }

  "getUserWithUserId " should "eventually return none if case of invalid userId." in {
    val expectedResult =  ""
    val obtainedResult = for(listOfUsers <- UserAPI.users) yield UserAPI.getUserWithUserId(listOfUsers, 11)
    val obResult = obtainedResult.map {
      case Some(value) => value.name
      case None => ""
    }
    obResult.map(obtained => assert(expectedResult == obtained))
  }

  "getUserWithMaxPostComments " should "eventually return id of user with maximum comments on a post." in {
    val expectedResult: (String, Long, Int) = ("Clementina DuBuque", 100, 5)
    val obtainedResult = UserAPI.getUserWithMaxPostComments
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

}
