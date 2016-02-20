name := """taffeit"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  filters,
  cache,
  "com.typesafe.play" % "anorm_2.11" % "2.5.0",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.3.1"
)


libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1100-jdbc41"


lazy val root = project.in(file(".")).enablePlugins(PlayScala)
