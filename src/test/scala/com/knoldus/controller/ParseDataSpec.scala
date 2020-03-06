package com.knoldus.controller

import com.knoldus.model.Comments
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future
import scala.io.Source

class ParseDataSpec extends AsyncFlatSpec with MockitoSugar with BeforeAndAfterAll {
  val mockGetDataFromURL: GetDataFromUrl = mock[GetDataFromUrl]
  var data: String = _
  var parsedList: List[Comments] = _
  var fileName: String = _

  override protected def beforeAll(): Unit = {
    fileName = "./src/test/resources/samplejson.txt"
    val source = Source.fromFile(fileName)
    data = source.getLines().mkString
    val comment1 = Comments(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz"
      , "laudantium enim quasi est quidem magnam voluptate ipsam eos\n" +
        "tempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et " +
        "nam sapiente accusantium")
    val comment2 = Comments(1, 2, "quo vero reiciendis velit similique earum", "Jayne_Kuhic@sydney.com"
      , "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at" +
        "\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et")
    parsedList = List(comment1, comment2)
  }

  "parseData" should "eventually return list of given type." in {
    when(mockGetDataFromURL.getDataFromUrl(fileName)).thenReturn(Future(data))
    val expectedResult = parsedList
    val actualResult = ParseData.parseData[Comments](fileName, mockGetDataFromURL)
    for (actual <- actualResult) yield assert(actual == expectedResult)
  }

  "parseData" should "eventually return empty list in case of invalid URL." in {
    val invalidFilename = "fileName"
    when(mockGetDataFromURL.getDataFromUrl(invalidFilename)).thenReturn(Future(-1.toString))
    val expectedResult = List.empty[Comments]
    val actualResult = ParseData.parseData[Comments](invalidFilename , mockGetDataFromURL)
    for (actual <- actualResult) yield assert(actual == expectedResult)
  }

}
