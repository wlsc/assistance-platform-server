name := """assistance-platform-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.postgresql" % "postgresql" % "9.2-1003-jdbc4",
  "com.nimbusds" % "nimbus-jose-jwt" % "3.6",
  "commons-dbutils" % "commons-dbutils" % "1.6"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true