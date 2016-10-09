maintainer := "Wladimir Schmidt"
organization := "telecooperation"
name := "assistance-platform-service"
version := "1.0"
scalaVersion := "2.11.6"

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
  "org.apache.commons" % "commons-lang3" % "3.4",
  "de.tudarmstadt.informatik.tk.assistanceplatform" % "SharedAssistancePlatformAPI" % "0.0.2-SNAPSHOT" from "file:///home/x02/dev/assistance-platform-server/assistance-platform-service/lib/SharedAssistancePlatformAPI-0.0.2-SNAPSHOT.jar"
)

javaOptions in Universal ++= Seq(
  //"-J-Xmx1024m",
  //"-J-Xms512m",
  "-Dconfig.resource=application.conf",
  "-Dsbt.jse.engineType=Node",
  "-Dsbt.jse.command=$(which node)"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

fork in run := true

enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

lazy val root = (project in file(".")).enablePlugins(PlayJava)

docker <<= docker.dependsOn(Keys.`package`.in(Compile, packageBin))

// Define a Dockerfile
dockerfile in docker := {
  val jarFile = artifactPath.in(Compile, packageBin).value
  val classpath = (managedClasspath in Compile).value
  val mainclass = mainClass.in(Compile, packageBin).value.get
  val libs = "/app/libs"
  val jarTarget = "/app/" + jarFile.name

  new Dockerfile {
    from("java")
    expose(9000)
    classpath.files.foreach { 
      depFile =>
        val target = file(libs) / depFile.name
        stageFile(depFile, target)
    }
    addRaw(libs, libs)
    add(jarFile, jarTarget)
    val classpathString = s"$libs/*:$jarTarget"
    cmd("java", "-cp", classpathString, mainclass)
  }
}

imageNames in docker := Seq(
  ImageName(s"${organization.value}/${name.value}:latest"),
  ImageName(
    namespace = Some(organization.value),
    repository = name.value,
    tag = Some("v" + version.value)
  )
)