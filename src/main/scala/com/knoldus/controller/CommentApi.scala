package com.knoldus.controller

import com.knoldus.model.Comments
import net.liftweb.json.{DefaultFormats, parse}

class CommentApi(url: String) extends GetDataFromUrl {

  def getListOfParsedComments: List[Comments] = {
    implicit val format: DefaultFormats.type = DefaultFormats
    val commentData = parse(getDataFromUrl(url))
    commentData.children.map(comment => comment.extract[Comments])
  }


}
