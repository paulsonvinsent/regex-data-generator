name := "scala-learner"

version := "0.1"

scalaVersion := "2.11.1"

val sparkVersion = sys.props.getOrElse("spark.version", "2.2.0")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.9",
  "com.github.mifmif" % "generex" % "1.0.1"
)



