#!/usr/bin/env scalas

/***
resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
scalaVersion := "2.12.7"
libraryDependencies += "org.web3j" % "core" % "4.1.0"
*/

import org.web3j.crypto._
import org.web3j.utils.Numeric

def signMessage(hash: String, privateKey: String) = {
  val credentials = Credentials.create(privateKey)
  val sigData = Sign.signMessage(
    Numeric.hexStringToByteArray(hash),
    credentials.getEcKeyPair,
    false
  )

  val sigBytes = sigData.getV +: (sigData.getR ++ sigData.getS)
  Numeric.toHexString(sigBytes)
}

val signer = "0xf5B3ab72F6E80d79202dBD37400447c11618f21f"
val hash = "0x175f896da03d15c07729218625f700ef0b389dd3f244b687ba72f85d9f09b914"
val privateKey = "0x2949899bb4312754e11537e1e2eba03c0298608effeab21620e02a3ef68ea58a"

val sig = signMessage(hash, privateKey)
println(s"sig: $sig")
