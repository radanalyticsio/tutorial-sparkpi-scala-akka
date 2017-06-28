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
       -p APP_FILE=scala-spark-webapp-assembly-0.1.jar
   ```

1. Expose an external route
   ```bash
   oc expose svc/scala-spark-webapp
   ```

1. Visit the exposed URL with your browser or other HTTP tool, for example:
   ```bash
   $ curl http://`oc get routes/scala-spark-webapp --template='{{.spec.host}}'`
   Pi is roughly 3.1335
   ```
