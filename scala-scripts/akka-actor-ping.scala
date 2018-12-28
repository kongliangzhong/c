#!/usr/bin/env scalas

/***
resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
scalaVersion := "2.12.7"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.19",
*/

import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props, PoisonPill }
import akka.event.Logging

case object Ping
case object Pong

class Pinger extends Actor {
  var countDown = 10

  def receive = {
    case Pong ⇒
      println(s"${sender()} received pong, count down $countDown")

      if (countDown > 0) {
        countDown -= 1
        sender() ! Ping
      } else {
        sender() ! PoisonPill
        self ! PoisonPill
      }
  }
}

class Ponger(pinger: ActorRef) extends Actor {
  def receive = {
    case Ping ⇒
      println(s"${sender()} received ping")
      pinger ! Pong
  }
}

val system = ActorSystem("pingpong")

val pinger = system.actorOf(Props(new Pinger), "pinger")

val ponger = system.actorOf(Props(new Ponger(pinger)), "ponger")

ponger ! Ping

// pinger ! Pong
