name := "symspell"

version := "0.1"

scalaVersion := "2.12.3"

val univocity = "com.univocity" % "univocity-parsers" % "2.5.8"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += scalatest
libraryDependencies += univocity