package com.knoldus.controller

import com.knoldus.model.Users
import net.liftweb.json.{DefaultFormats, parse}
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class UserApi(val url: String) {

  def getUsers: String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build();
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  def getListOfParsedUsers: List[Users] = {
    implicit val format: DefaultFormats.type = DefaultFormats
    val userData = parse(getUsers)
    (userData).children.map(user => user.extract[Users])
  }

}
