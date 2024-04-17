import sbt.url

name := jsonHandler.value.stringValue("package.json", "name")

organization := jsonHandler.value.stringValue("package.json", "organization")

val MongoCampHomepage = "https://www.mongocamp.dev"

organizationHomepage := Some(url(MongoCampHomepage))

homepage := Some(url("https://mongodb-driver.mongocamp.dev"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/MongoCamp/mongodb-driver"),
    "scm:https://github.com/MongoCamp/mongodb-driver.git"
  )
)

developers := List(
  Developer(
    id = "mongocamp",
    name = "MongoCamp-Team",
    email = "info@mongocamp.dev",
    url = url(MongoCampHomepage)
  ),
  Developer(
    id = "sfxcode",
    name = "Tom",
    email = "tom@mongocamp.dev",
    url = url(MongoCampHomepage)
  ),
  Developer(
    id = "quadstingray",
    name = "QuadStingray",
    email = "simon@mongocamp.dev",
    url = url(MongoCampHomepage)
  )
)

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

crossScalaVersions := Seq("2.13.13", "2.12.17")

scalaVersion := crossScalaVersions.value.head

scalacOptions += "-deprecation"

Test / parallelExecution := false

lazy val mongodb = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys ++= Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "dev.mongocamp"
  )

buildInfoOptions += BuildInfoOption.BuildTime

resolvers += "Sonatype OSS Snapshots".at("https://oss.sonatype.org/content/repositories/snapshots")

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.20.5" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.3" % Test

libraryDependencies += "joda-time" % "joda-time" % "2.12.7"

val circeVersion = "0.14.6"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion % Test)

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "5.0.0"

libraryDependencies += "org.xerial.snappy" % "snappy-java" % "1.1.10.5" % Provided

libraryDependencies += "com.github.luben" % "zstd-jni" % "1.5.5-11" % Provided

libraryDependencies += "org.apache.lucene" % "lucene-queryparser" % "9.10.0"

val MongoJavaServerVersion = "1.44.0"

libraryDependencies += "de.bwaldvogel" % "mongo-java-server" % MongoJavaServerVersion % Provided

libraryDependencies += "de.bwaldvogel" % "mongo-java-server-h2-backend" % MongoJavaServerVersion % Provided

libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.2"

libraryDependencies += "com.typesafe" % "config" % "1.4.3"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"

libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.11.0"

libraryDependencies += "com.vdurmont" % "semver4j" % "3.1.0"

// Mongo SQL Converter
val MongoDbVersion = "4.3.1"
libraryDependencies += "com.github.jsqlparser" % "jsqlparser" % "4.3"
//libraryDependencies += "com.google.code.gson" % "gson" % "2.8.9"
//libraryDependencies += "com.google.guava" % "guava" % "32.0.0-jre"
//libraryDependencies += "commons-io" % "commons-io" % "2.11.0"
//libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.12.0"
//libraryDependencies += "org.mongodb" % "bson" % MongoDbVersion
//libraryDependencies += "org.mongodb" % "mongodb-driver-sync" % MongoDbVersion

buildInfoPackage := "dev.mongocamp.driver.mongodb"

buildInfoOptions += BuildInfoOption.BuildTime

scalafmtOnCompile := false

coverageMinimumStmtTotal := 70

coverageFailOnMinimum := true

jsonFiles += (baseDirectory.value / "package.json")
