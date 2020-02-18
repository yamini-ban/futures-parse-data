package com.knoldus.controller

import com.knoldus.model.Comments
import net.liftweb.json.{DefaultFormats, parse}
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class CommentApi(url: String) {

  def getComments: String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build();
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  def getListOfParsedComments: List[Comments] = {
    implicit val format: DefaultFormats.type = DefaultFormats
    val commentData = parse(getComments)
    commentData.children.map(comment => comment.extract[Comments])
  }
}
