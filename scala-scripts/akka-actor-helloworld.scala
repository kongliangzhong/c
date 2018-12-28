#!/usr/bin/env scalas

/***
resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
scalaVersion := "2.12.7"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.19",
*/

import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }
import akka.event.Logging

class MyActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" => log.info("test received")
    case _ => log.info("unknown message")
  }
}

val system = ActorSystem("root")
val myActor = system.actorOf(Props[MyActor], "myActor1")

myActor ! "test"
myActor ! "fdsjfklds"
