name := "symspell"

version := "0.1"

scalaVersion := "2.12.4"

val univocity = "com.univocity" % "univocity-parsers" % "2.5.8"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.4" % "test"
val java_string_similarity = "info.debatty" % "java-string-similarity" % "1.0.0"

libraryDependencies += scalatest
libraryDependencies += univocity
libraryDependencies += java_string_similarity