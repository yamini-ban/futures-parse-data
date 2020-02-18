package com.knoldus.model

case class Users(
                  id: Long,
                  name: String,
                  username: String,
                  email: String,
                  address: Address,
                  phone: String,
                  website: String,
                  company: Company
                )
