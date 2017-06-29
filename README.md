# scala-spark-webapp
A Scala webapp using akka-http to launch a SparkPi computation from a REST endpoint (GET)

This application is an example tutorial for the [radanalytics.io](http://radanalytics.io) community. 
It is intended to be used as a source-to-image (s2i) application.

## Quick start

You should have access to an OpenShift cluster and be logged in with the
`oc` command line tool.

1. Create the necessary infrastructure objects
   ```bash
   oc create -f http://radanalytics.io/resources.yaml
   ```

1. Launch scala-spark-webapp
   ```bash
   oc new-app --template oshinko-scala-spark-build-dc \
   -p APPLICATION_NAME=scala-spark-webapp \
   -p GIT_URI=https://github.com/pdmack/scala-spark-webapp \
   -p APP_MAIN_CLASS=io.radanalytics.examples.akka.sparkpi.WebServerHttpApp \
   -p APP_FILE=scala-spark-webapp_2.11-0.1.jar \
   -p SPARK_OPTIONS=" --packages com.typesafe.akka:akka-http_2.11:10.0.9,com.typesafe.akka:akka-http-xml_2.11:10.0.9,com.typesafe.akka:akka-stream_2.11:2.5.3 --conf spark.jars.ivy=/tmp/.ivy2 "
   ```

1. Expose an external route
   ```bash
   oc expose svc/scala-spark-webapp
   ```

1. Visit the exposed URL with your browser or other HTTP tool, for example:
   ```bash
   $ curl http://`oc get routes/scala-spark-webapp --template='{{.spec.host}}'`
     Scala Akka SparkPi server running. Add the 'sparkpi' route to this URL to invoke the app.
   $ curl http://`oc get routes/scala-spark-webapp --template='{{.spec.host}}'`/sparkpi
     Pi is roughly 3.1458957294786476  
   ```
