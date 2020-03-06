package com.knoldus.model

import java.util.Date

case class Status(createdAt: Date, favoriteCount: Int, retweetCount: Int) {
  def getCreatedAt: Date = {
    createdAt
  }
  def getFavoriteCount: Int = {
    favoriteCount
  }
  def getRetweetCount: Int = {
    retweetCount
  }
}
