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
}

object Dependencies {
  import Resolvers._
  import Versions._

  val guava        = "com.google.guava"  % "guava"          % Versions.guava

  // testing
  val scalaTest = "org.scalatest"    %% "scalatest"      % "1.7.RC1" % "test"
  val mockito   = "org.mockito"       % "mockito-core"   % "1.8.5"   % "test"

}

object BuildSettings {
  import Resolvers._
  import Dependencies._

  val generalDependencies = Seq(
    guava,
    scalaTest, mockito
  )
  
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
    "kanbanery",
    file("."),
    settings = buildSettings ++
      Seq (
        libraryDependencies ++=  generalDependencies ++ Seq()
      )
  )

}
