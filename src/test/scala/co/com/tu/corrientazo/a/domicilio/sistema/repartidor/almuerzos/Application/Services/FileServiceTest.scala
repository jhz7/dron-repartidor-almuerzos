package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import org.scalatest.{MustMatchers, WordSpecLike}

class FileServiceTest extends MustMatchers with WordSpecLike {

  "FileService" should {

    "writeLinesToFile" should {

      "When starts to write a file " +
        "Where the file does not exist" +
        "Then the file is created " in {

        val linesToWrite = List("Foo", "Bar", "Baz")
        val idFile = "test"

        FileService.writeLinesToFile( linesToWrite, idFile )
      }
    }

    "readLinesFromFile" should {

      "When starts to read a file " +
        "Where the file exists" +
        "Then it must return lines readed" in {

        val linesExpected = List("Foo", "Bar", "Baz")
        val idFile = "test"

        val linesReaded = FileService.readLinesFromFile( idFile )

        assertResult( linesExpected )( linesReaded )
      }

      "When starts to read a file " +
        "Where the file does not exists" +
        "Then it must return aan empty list " in {

        val idFile = "not-exists"

        val linesReaded = FileService.readLinesFromFile( idFile )

        linesReaded mustBe Nil
      }
    }
  }
}
