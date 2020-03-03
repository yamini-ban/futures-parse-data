package com.knoldus

import com.knoldus.controller.CommentApi
import com.knoldus.model.Comments
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class CommentApiSpec extends AsyncFlatSpec with MockitoSugar with BeforeAndAfterAll {

  var listOfComments: Future[List[Comments]] = _
  var commentApi: CommentApi = _

  override protected def beforeAll(): Unit = {
    val comment1 = Comments(1, 1, "comment1", "abc", "body1")
    val comment2 = Comments(2, 2, "comment2", "abc", "body2")
    val comment3 = Comments(2, 3, "comment3", "abc", "body3")
    val comment4 = Comments(3, 4, "comment4", "abc", "body4")
    listOfComments = Future(List(comment1, comment2, comment3, comment4))

    commentApi = new CommentApi(listOfComments)
  }

  "getComments" should "eventually return list of type comments." in {
    val expectedResult = listOfComments
    val actualResult = commentApi.getComments
    for(expected <- expectedResult; actual <- actualResult) yield assert(actual == expected)
  }
}
