name := """projectClientVirusTotalApi"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Add by malex for communicate with http
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "1.1.5"

// Add by malex to use PLAY JSON
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.4.3"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

