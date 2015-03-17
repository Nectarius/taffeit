name := """taffeit"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.3.1"
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1100-jdbc41"



lazy val root = project.in(file(".")).enablePlugins(PlayScala)
