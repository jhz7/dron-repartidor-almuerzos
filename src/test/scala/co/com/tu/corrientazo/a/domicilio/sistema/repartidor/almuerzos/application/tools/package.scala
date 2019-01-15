package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application

import java.io.File
import java.util

import scala.io.Source
import scala.util.Try

package object tools {

  def readLinesFromFileInOutDir( idFile: String ): List[String] = {

    val fileToRead = s"src/resources/out/out$idFile.txt"

    Try( Source.fromFile( fileToRead ).getLines().toList ).getOrElse( Nil )
  }

  def cleanOutDir(): Unit = util.Arrays.stream(new File("src/resources/out/").listFiles()).forEach(f => f.delete())
}
