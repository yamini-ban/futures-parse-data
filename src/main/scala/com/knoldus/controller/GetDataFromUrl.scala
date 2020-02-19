package com.knoldus.controller

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

trait GetDataFromUrl {

  def getDataFromUrl(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build();
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }
}
