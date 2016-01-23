/// Docker Stuff
maintainer := "Bennet Jeutter" // Maintainer
dockerExposedPorts := Seq(9000, 9443) // Expose Ports
dockerUpdateLatest := true
dockerRepository := Option("m156")

name := """assistance-platform-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.10.5"

resolvers += (
    "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
)

resolvers += "Assi nexus" at "http://130.83.163.56:9001/content/repositories/public/"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.postgresql" % "postgresql" % "9.2-1003-jdbc4",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.2",
  "commons-dbutils" % "commons-dbutils" % "1.6",
  "de.tudarmstadt.informatik.tk.assistanceplatform" % "SharedAssistancePlatformAPI" % "0.0.2-SNAPSHOT",
  "commons-lang" % "commons-lang" % "2.2"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true