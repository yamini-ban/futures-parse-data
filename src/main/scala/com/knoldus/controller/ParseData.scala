package com.knoldus.controller

import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ParseData[B] extends GetDataFromUrl {
  def parseData(url: String)(implicit m: Manifest[B]): Future[List[B]] = {
    implicit val format: DefaultFormats.type = DefaultFormats
    val data = for (dataFromUrl <- getDataFromUrl(url)) yield{
      val data = parse(dataFromUrl)
      data.children.map(d => d.extract[B])
    }
    data.recover({
      case _: Exception => List.empty[B]
    })
  }

}
