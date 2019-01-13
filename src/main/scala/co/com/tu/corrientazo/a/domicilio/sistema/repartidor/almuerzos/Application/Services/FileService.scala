package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import java.io.{File, PrintWriter}

import scala.io.Source
import scala.util.Try

object FileService {

  def readLinesFromFile( idFile: String ): List[String] = {

    val fileToRead = s"src/resources/in/in$idFile.txt"

    Try( Source.fromFile( fileToRead ).getLines().toList ).getOrElse( Nil )
  }

  def writeLinesToFile( linesToWrite: List[String], idFile: String ): Unit = {

    val fileToWrite = s"src/resources/out/out$idFile.txt"
    val content = linesToWrite.mkString( "\n" )
    val pw = new PrintWriter( new File( fileToWrite ))

    try pw.write( content ) finally pw.close()
  }
}
