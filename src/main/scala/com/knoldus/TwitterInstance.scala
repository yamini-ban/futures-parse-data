package com.knoldus

import com.typesafe.config.ConfigFactory
import twitter4j.{Twitter, TwitterFactory}
import twitter4j.auth.AccessToken

trait TwitterInstance {
  def getTwitterInstance: Twitter
}

object TwitterInstance extends TwitterInstance {
  def getTwitterInstance: Twitter = {
    val config = ConfigFactory.load()
    val twitter: Twitter = new TwitterFactory().getInstance()
    // Authorising with your Twitter Application credentials
    twitter.setOAuthConsumer(config.getString("consumer.key"), config.getString("consumer.secret"))
    twitter.setOAuthAccessToken(new AccessToken(config.getString("token.key"), config.getString("token.secret")))
    twitter
  }
}