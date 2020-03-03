package com.knoldus.controller

import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ParseData {
  def parseData[B](url: String)(implicit m: Manifest[B]): Future[List[B]]
}

object ParseData extends ParseData {
  def parseData[B](url: String)(implicit m: Manifest[B]): Future[List[B]] = {
    implicit val format: DefaultFormats.type = DefaultFormats
    val content = Future(GetDataFromUrl.getDataFromUrl(url))
    val data = for (dataFromUrl <- content) yield{
      val data = parse(dataFromUrl)
      data.children.map(d => d.extract[B])
    }
    data.recover({
      case _: Exception => List.empty[B]
    })
  }
}
