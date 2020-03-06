package com.knoldus.controller

import com.knoldus.model.{Comments, Posts}
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class PostApiSpec extends AsyncFlatSpec with MockitoSugar with BeforeAndAfterAll {

  val mockedCommentApi: CommentApi = mock[CommentApi]
  var listOfPosts: List[Posts] = _
  var postApi: PostsApi = _
  var listOfComments: Future[List[Comments]] = _

  override protected def beforeAll(): Unit = {
    val post1 = Posts(1, 1, "post1", "description of post1")
    val post2 = Posts(2, 2, "post2", "description of post2")
    val post3 = Posts(1, 3, "post3", "description of post3")
    listOfPosts = List(post1, post2, post3)

    postApi = new PostsApi(Future(listOfPosts), mockedCommentApi)

    val comment1 = Comments(1, 1, "comment1", "abc", "body1")
    val comment2 = Comments(2, 2, "comment2", "abc", "body2")
    val comment3 = Comments(2, 3, "comment3", "abc", "body3")
    val comment4 = Comments(3, 4, "comment4", "abc", "body4")
    listOfComments = Future(List(comment1, comment2, comment3, comment4))
  }

  "getPosts" should "eventually return list of posts." in {
    val expectedResult = Future(listOfPosts)
    val actualResult = postApi.getPosts
    for(expected <- expectedResult; actual <- actualResult) yield assert(actual == expected)
  }

  "getPostWithMaxCommentCount" should "eventually return post id with max comment count." in {
    when(mockedCommentApi.getComments).thenReturn(listOfComments)
    val expectedResult: (Long, Int) = (2, 2)
    val actualResult = postApi.getPostWithMaxCommentCount
    actualResult.map(obResult => assert(obResult == expectedResult))
  }

  "getPostWithPostId" should "eventually return post with given post id." in {
    val expectedResult = 1
    val postId = 3
    val actualResult = for (list <- postApi.getPosts) yield postApi.getPostWithPostId(list, postId)
    val obResult = actualResult.map {
      case Some(value) => value.userId
      case None => -1
    }
    obResult.map(result => assert(expectedResult == result))
  }

  "getPostWithPostId" should "eventually return none in case of invalid post id." in {
    val expectedResult = -1
    val invalidPostId = 4
    val actualResult = for (listOfPosts <- postApi.getPosts) yield postApi.getPostWithPostId(listOfPosts, invalidPostId)
    val obResult = actualResult.map {
      case None => -1
      case Some(value) => value.userId
    }
    obResult.map(result => assert(expectedResult == result))
  }

}
