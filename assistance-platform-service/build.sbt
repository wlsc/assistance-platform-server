maintainer := "Bennet Jeutter"

organization := "telecooperation"

name := "assistance-platform-service"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

dockerfile in docker := {
  val appDir = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("java")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy(appDir, targetDir)
  }
}

buildOptions in docker := BuildOptions(cache = false)

resolvers += (
  "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
  )

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.26.1",
  "commons-dbutils" % "commons-dbutils" % "1.6",
  "org.apache.commons" % "commons-lang3" % "3.4"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true