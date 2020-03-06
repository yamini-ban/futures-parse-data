name := "twitter"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"

// https://mvnrepository.com/artifact/org.mockito/mockito-scala
libraryDependencies += "org.mockito" %% "mockito-scala" % "1.11.4" % Test

libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.11"

libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.0"
//libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.7"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.6"

// https://mvnrepository.com/artifact/com.typesafe/config
libraryDependencies += "com.typesafe" % "config" % "1.4.0"
