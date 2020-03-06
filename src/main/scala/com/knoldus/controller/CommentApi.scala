package com.knoldus.controller

import com.knoldus.model.Comments

import scala.concurrent.Future

class CommentApi(comments: Future[List[Comments]]) {
  def getComments: Future[List[Comments]] = {
    comments
  }
}