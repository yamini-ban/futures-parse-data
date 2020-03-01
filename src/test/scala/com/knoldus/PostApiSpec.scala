package com.knoldus

import org.scalatest.flatspec.AsyncFlatSpec

class PostApiSpec extends AsyncFlatSpec {

  "getPostWithMaxCommentCount" should "eventually return id of post with maximum comment count." in {
    val expectedResult: (Long, Int) = (100,5)
    val obtainedResult = PostsAPI.getPostWithMaxCommentCount
    obtainedResult.map(obResult => assert(expectedResult == obResult))
  }

  "getPostWithPostId" should "eventually return post with given post id." in {
    val expectedResult = 10
    val postId = 100
    val obtainedResult = for (listOfPosts <- PostsAPI.posts) yield PostsAPI.getPostWithPostId(listOfPosts, postId)
    val obResult = obtainedResult.map {
      case Some(value) => value.userId
      case None => -1
    }
    obResult.map(result => assert(expectedResult == result))
  }

  "getPostWithPostId" should "eventually return none in case of invalid post id." in {
    val expectedResult = -1
    val invalidPostId = 101
    val obtainedResult = for (listOfPosts <- PostsAPI.posts) yield PostsAPI.getPostWithPostId(listOfPosts, invalidPostId)
    val obResult = obtainedResult.map {
      case Some(value) => value.userId
      case None => -1
    }
    obResult.map(result => assert(expectedResult == result))
  }

}
