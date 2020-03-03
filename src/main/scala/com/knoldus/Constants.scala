package com.knoldus

import com.knoldus.model.{Address, Company, Geo, Users}

object Constants {
  val DefaultUser: Users = Users(-1, "", "", ""
    , Address("", "", "", "", Geo("", ""))
    , "", "", Company("", "", ""))

  val userUrl = "https://jsonplaceholder.typicode.com/users"
  val postUrl = "https://jsonplaceholder.typicode.com/posts"
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"
}
