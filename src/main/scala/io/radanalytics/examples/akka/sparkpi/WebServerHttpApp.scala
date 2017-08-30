package io.radanalytics.examples.akka.sparkpi

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqMarshaller
import akka.http.scaladsl.server.{ HttpApp, Route }

import scala.math.random
import org.apache.spark.sql.SparkSession

/**
 * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
 * and it will be shutdown after pressing return.
 */
object WebServerHttpApp extends HttpApp with App {

  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete("Scala Akka SparkPi server running. Add the 'sparkpi' route to this URL to invoke the app.")
    } ~
      path("sparkpi") { // Listens to paths that are exactly `/sparkpi`
        get { // Listens only to GET requests
          parameters("partitions".as[Int] ? 2) { partitions => // Accept the "partitions" query parameter
            val spark = SparkSession.builder.appName("Scala SparkPi WebApp").getOrCreate()
            val n = math.min(100000L * partitions, Int.MaxValue).toInt // avoid overflow
            val count = spark.sparkContext.parallelize(1 until n, partitions).map { i =>
              val x = random
              val y = random
              if (x * x + y * y < 1) 1 else 0
            }.reduce(_ + _)
            spark.stop()
            complete("Pi is roughly " + 4.0 * count / (n - 1))
          }
        }
      }

  // This will start the server until the return key is pressed
  startServer("0.0.0.0", 8080)
}
