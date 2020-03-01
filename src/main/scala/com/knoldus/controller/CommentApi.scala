package com.knoldus.controller

import com.knoldus.model.Comments

import scala.concurrent.Future

class CommentApi(url: String) extends ParseData[Comments] {

  def getListOfParsedComments: Future[List[Comments]] = {
    parseData(url)
  }

}
