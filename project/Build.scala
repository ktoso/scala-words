import sbt._
import sbt.Keys._

object Resolvers {
  val kanbaneryResolvers = Seq(
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"
  )
}

object Versions {
  val guava = "12.0"
  val akka = "2.1-M1"
  val liftVersion = "2.4"
}

object Dependencies {
  import Resolvers._
  import Versions._

  val guava                 = "com.google.guava"  % "guava"          % Versions.guava

  val scalaRainbow          = "pl.project13.scala"     % "rainbow_2.9.1"          % "0.1"

  val slf4s                 = "com.weiglewilczek.slf4s" %% "slf4s"                % "1.0.7"
  val logback               = "ch.qos.logback"        % "logback-classic"         % "1.0.0"
  val log4jOverSlf4j        = "org.slf4j"             % "log4j-over-slf4j"        % "1.6.1"
  val jclOverSlf4j          = "org.slf4j"             % "jcl-over-slf4j"          % "1.6.1"
  val julToSlf4jBridge      = "org.slf4j"             % "jul-to-slf4j"            % "1.6.1"
  val logging               = Seq(slf4s, logback, log4jOverSlf4j, jclOverSlf4j)

//  val liftMongoRecord       = "net.liftweb"             %% "lift-mongodb-record"   % Versions.liftVersion
  val mongo                 = "org.mongodb"            %  "mongo-java-driver"     % "2.7.3"
  val rogue                 = "com.foursquare"         %% "rogue"                 % "1.1.8" intransitive()

  // testing
  val scalaTest = "org.scalatest"    %% "scalatest"      % "1.7.RC1" % "test"
  val mockito   = "org.mockito"       % "mockito-core"   % "1.8.5"   % "test"

}

object BuildSettings {
  import Resolvers._
  import Dependencies._

  val generalDependencies = Seq(
    guava,
    scalaRainbow,
    scalaTest, mockito
  ) ++ logging
  
  val buildSettings = Defaults.defaultSettings ++
    Seq(
      organization := "pl.project13.scala",
      name         := "words",
      version      := "0.1",
      scalaVersion := "2.9.1",
      resolvers    ++= Resolver.withDefaultResolvers(kanbaneryResolvers, mavenCentral = true, scalaTools = false),
      libraryDependencies ++= generalDependencies
    )
}

object ScalaWordsBuild extends Build {
  import Dependencies._
  import BuildSettings._

//  compileOrder := CompileOrder.JavaThenScala

  lazy val root = Project (
    "scala-words",
    file("."),
    settings = buildSettings ++
      Seq (
        libraryDependencies ++=  generalDependencies ++ Seq()
      )
  )

}
