name := "json-parsing"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

// https://mvnrepository.com/artifact/org.mockito/mockito-scala
libraryDependencies += "org.mockito" %% "mockito-scala" % "1.11.4" % Test


libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.11"

libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.0"
