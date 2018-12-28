#!/usr/bin/env scalas

/***
resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
scalaVersion := "2.12.7"
libraryDependencies += "org.scala-sbt" %% "io" % "1.2.2"
*/

import sbt.io.IO
import sbt.io.Path._
import sbt.io.syntax._
import java.io.File
import java.net.{URI, URL}
import sys.process._
def file(s: String): File = new File(s)
def uri(s: String): URI = new URI(s)

val targetDir = file("./")
val srcDir = file("./")
val toTarget = rebase(srcDir, targetDir)

def processFile(f: File): Unit = {
  val newParent = toTarget(f.getParentFile) getOrElse {sys.error("wat")}
  val file1 = newParent / f.name
  println(s"""$f => $file1""")
  val xs = IO.readLines(f) map { _ + "!" }
  IO.writeLines(file1, xs)
}

val fs: Seq[File] = (srcDir ** "*.scala").get
fs foreach { processFile }
