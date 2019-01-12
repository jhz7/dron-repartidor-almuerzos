package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import org.scalatest.{MustMatchers, WordSpecLike}

class FileServiceTest extends MustMatchers with WordSpecLike {

  "FileService" should {

    "writeLinesToFile" should {

      "" in {

        val linesToWrite = List("abc\n", "def")

        //FileService.writeLinesToFile( linesToWrite, idFile = "o1" )

        FileService.write( "01", "hola\nhello\nhi" )

        val x = 1
      }
    }
  }
}
