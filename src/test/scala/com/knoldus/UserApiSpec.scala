package com.knoldus

import com.knoldus.controller.{PostsApi, UserApi}
import com.knoldus.model._
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class UserApiSpec extends AsyncFlatSpec with MockitoSugar with BeforeAndAfterAll {

  val mockedPostApi: PostsApi = mock[PostsApi]
  var listOfPosts: List[Posts] = _
  var listOfUser: List[Users] = _
  var userApi: UserApi = _

  override protected def beforeAll(): Unit = {
    val post1 = Posts(1, 1, "post1", "description of post1")
    val post2 = Posts(2, 2, "post2", "description of post2")
    val post3 = Posts(1, 3, "post3", "description of post3")
    listOfPosts = List(post1, post2, post3)

    val user1 = Users(1, "user1", "a", "a.a@a.a"
      , Address("street", "suite", "city", "zip", Geo("lat", "lng"))
      , "ph", "website", Company("c", "phrase", "bs"))
    val user2 = Users(2, "user2", "a", "a.a@a.a"
      , Address("street", "suite", "city", "zip", Geo("lat", "lng"))
      , "ph", "website", Company("c", "phrase", "bs"))
    listOfUser = List(user1, user2)

    userApi = new UserApi(Future(listOfUser), mockedPostApi)
  }

  "getUsers" should "eventually return list of users." in {
    val expectedResult = Future(listOfUser)
    val actualResult = userApi.getUsers
    for(expected <- expectedResult; actual <- actualResult) yield assert(actual == expected)
  }

  "getUserWithMaximumPostsCount " should "eventually return id of user with posts count." in {
    when(mockedPostApi.getPosts).thenReturn(Future(listOfPosts))
    val expectedResult = (1,2)
    val obtainedResult = userApi.getUserWithMaximumPostsCount
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

  "getListOfUsersWithPostIds " should "eventually return list of id of user with post ids." in {
    when(mockedPostApi.getPosts).thenReturn(Future(listOfPosts))
    val expectedResult: List[UserWithPosts] = List(UserWithPosts(1,List(1, 3)), UserWithPosts(2,List(2)))
    val obtainedResult = userApi.getListOfUsersWithPostIds
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

  "getUserWithUserId " should "eventually return user with given userId." in {
    when(mockedPostApi.getPosts).thenReturn(Future(listOfPosts))
    val expectedResult =  "user2"
    val obtainedResult = for(listOfUsers <- userApi.getUsers) yield userApi.getUserWithUserId(listOfUsers, 2)
    val obResult = obtainedResult.map {
      case Some(value) => value.name
      case None => ""
    }
    obResult.map(obtained => assert(expectedResult == obtained))
  }

  "getUserWithUserId " should "eventually return none if case of invalid userId." in {
    when(mockedPostApi.getPosts).thenReturn(Future(listOfPosts))
    val expectedResult =  ""
    val invalidUserId = 11
    val obtainedResult = for(listOfUsers <- userApi.getUsers) yield userApi.getUserWithUserId(listOfUsers, invalidUserId)
    val obResult = obtainedResult.map {
      case Some(value) => value.name
      case None => ""
    }
    obResult.map(obtained => assert(expectedResult == obtained))
  }

  "getUserWithMaxPostComments " should "eventually return id of user with maximum comments on a post." in {
    val post = Posts(2, 2, "post2", "description of post2")
    val postWithMaxComments: (Long, Int) = (2,10)
    when(mockedPostApi.getPosts).thenReturn(Future(listOfPosts))
    when(mockedPostApi.getPostWithMaxCommentCount).thenReturn(Future(postWithMaxComments))
    when(mockedPostApi.getPostWithPostId(listOfPosts, 2)).thenReturn(Option(post))
    val expectedResult: (String, Long, Int) = ("user2", 2, 10)
    val obtainedResult = userApi.getUserWithMaxPostComments
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

  "getUserWithMaxPostComments " should "eventually throw an exception in case of no user-post association." in {
    val postWithMaxComments: (Long, Int) = (4,10)
    when(mockedPostApi.getPosts).thenReturn(Future(listOfPosts))
    when(mockedPostApi.getPostWithMaxCommentCount).thenReturn(Future(postWithMaxComments))
    when(mockedPostApi.getPostWithPostId(listOfPosts, 4)).thenReturn(None)
    val expectedResult: String = ""
    val obtainedResult = userApi.getUserWithMaxPostComments
    obtainedResult.map(obResult => assert(expectedResult == obResult._1))

  }

}
