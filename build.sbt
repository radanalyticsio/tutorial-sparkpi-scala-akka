lazy val akkaHttpVersion = "10.0.9"
lazy val akkaVersion    = "2.5.3"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaHome       := Some(file("/opt/scala")),
      scalaVersion    := "2.11.8",
      organization    := "io.radanalytics.examples.akka.sparkpi"
    )),
    name := "scala-spark-webapp",
    version := "0.1",
    mainClass in assembly := Some("io.radanalytics.examples.akka.sparkpi.WebServerHttpApp"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"       % akkaVersion,
      "org.apache.spark"  %  "spark-sql_2.11"     % "2.1.0"  % "provided"
    )
  )
