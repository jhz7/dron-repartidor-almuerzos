package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application

import scala.io.Source
import scala.util.Try

package object Tools {

  def readLinesFromFileInOutDir( idFile: String ): List[String] = {

    val fileToRead = s"src/resources/out/out$idFile.txt"

    Try( Source.fromFile( fileToRead ).getLines().toList ).getOrElse( Nil )
  }
}
