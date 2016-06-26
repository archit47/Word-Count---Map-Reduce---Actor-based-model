name := "Word Count - Map Reduce (Actor based model)"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


libraryDependencies ++= {
  val akkaV       = "2.4.7"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-agent" % akkaV,
    "com.typesafe.akka" %% "akka-remote" % akkaV
  )
}