package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import java.io.{ File, PrintWriter }

import scala.io.{ BufferedSource, Source }

object FileService {

  def readLinesFromFile( idFile: String ): List[String] = {
    val fileToRead = s"resources/in/in$idFile.txt"
    val bufferedSource: BufferedSource = Source.fromFile( fileToRead )
    
    bufferedSource.getLines().toList
  }

  def writeLinesToFile( linesToWrite: List[String], idFile: String ): Unit = {

    val fileToWrite = s"src/resources/out/out$idFile.txt"
    val pw = new PrintWriter(new File(fileToWrite))
    val content = linesToWrite.mkString("\n")

    try pw.write(content) finally pw.close()
  }
}
