package com.knoldus.controller

import com.knoldus.model.Posts
import net.liftweb.json.{DefaultFormats, parse}
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class PostsApi(val url: String) {

  def getPosts: String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build();
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  def getListOfParsedPosts: List[Posts] = {
    implicit val format: DefaultFormats.type = DefaultFormats
    val postData = parse(getPosts)
    postData.children.map(post => post.extract[Posts])
  }


}
