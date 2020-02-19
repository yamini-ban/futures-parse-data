package com.knoldus.controller

import com.knoldus.model.{PostWithComments, Posts}
import net.liftweb.json.{DefaultFormats, parse}
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

import scala.concurrent.Future

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

  def postsWithCommentIds = {
    posts.map(listOfPosts =>{
      val result = listOfPosts.map(post => {
        comments.map(listOfComments => PostWithComments(post.id, listOfComments.filter(_.postId == post.id).map(_.id)))
      })
      Future.sequence(result)
    }).flatten
  }

}
