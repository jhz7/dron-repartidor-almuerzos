package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.services

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.tools._
import org.scalatest.{BeforeAndAfter, MustMatchers, WordSpecLike}

class FileServiceTest extends MustMatchers with WordSpecLike with BeforeAndAfter {

  after {
    println( "After Test" )
    cleanOutDir()
  }

  "FileService" should {

    "writeLinesToFile" should {

      "When starts to write a file " +
        "Where the file does not exist" +
        "Then the file is created " in {

        val linesToWrite = List("Foo", "Bar", "Baz")
        val idFile = "test-file-service"

        FileService.writeLinesToFile( linesToWrite, idFile )

        val linesWrote = readLinesFromFileInOutDir( idFile )

        linesWrote mustBe linesToWrite
      }
    }

    "readLinesFromFile" should {

      "When starts to read a file " +
        "Where the file exists" +
        "Then it must return lines readed" in {

        val linesExpected = List("Foo", "Bar", "Baz")
        val idFile = "test-file-service"

        val linesReaded = FileService.readLinesFromFile( idFile )

        linesReaded mustBe linesExpected
      }

      "When starts to read a file " +
        "Where the file does not exists" +
        "Then it must return an empty list " in {

        val idFile = "not-exists"

        val linesReaded = FileService.readLinesFromFile( idFile )

        linesReaded mustBe Nil
      }
    }
  }
}
