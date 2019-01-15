package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.tools._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.executionScheduler
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.models.{DroneIdentifier, Position}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.types.{EAST, NORTH, SOUTH, WEST}
import org.scalatest.{BeforeAndAfter, MustMatchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class LunchDeliveryServiceTest extends MustMatchers with WordSpecLike with BeforeAndAfter {

  after {
    println("After Test")
    cleanOutDir()
  }

  "LunchDeliveryService" should {

    "deliverLunches" should {

      "This test is only to show that example given in exercise has a wrong result: " +
        "When starts to deliver lunches given a list of routes (taken from example) " +
        "Where the delivery is successfull " +
        "Then it must resturn a list with the places visited " in {

        val routes = List("AAAAIAAD", "DDAIAD", "AAIADAD")
        val droneIdentifier = DroneIdentifier(id = "1")
        val expectedResult = List(
          Position(-2, 4, NORTH),
          Position(-1, 3, SOUTH),
          Position(0, 0, WEST)
        )

        val resultado = LunchDeliveryService.deliverLunches(routes, droneIdentifier)

        resultado mustBe expectedResult.asRight
      }

      "When starts to deliver lunches given a list of routes " +
        "Where the delivery is successfull " +
        "Then it must resturn a list with the places visited " in {

        val routes = List("AADA", "IAD", "A", "A")
        val droneIdentifier = DroneIdentifier(id = "1")
        val expectedResult = List(
          Position(1, 2, EAST),
          Position(1, 3, EAST),
          Position(2, 3, EAST),
          Position(3, 3, EAST)
        )

        val resultado = LunchDeliveryService.deliverLunches(routes, droneIdentifier)

        resultado mustBe expectedResult.asRight
      }

      "When starts to deliver lunches given a list of routes " +
        "Where a route has an invalid motion (J) " +
        "Then it must resturn a n error " in {

        val routes = List("AAJDA", "IAD")
        val droneIdentifier = DroneIdentifier(id = "1")
        val expectedResult = ErrorMessage("Está intentando hacer un movimiento no permitido, por favor revisar las rutas establecidas. ")

        val resultado = LunchDeliveryService.deliverLunches(routes, droneIdentifier)

        resultado mustBe expectedResult.asLeft
      }

      "When starts to deliver lunches given a list of routes " +
        "Where a calculated position is out of allowed reange " +
        "Then it must resturn a n error " in {

        val routes = List("AADAAAAAAAAAAA", "IAD")
        val droneIdentifier = DroneIdentifier(id = "1")
        val expectedResult = ErrorMessage("La posición en el eje X a la que desea moverse es inválida. ")

        val resultado = LunchDeliveryService.deliverLunches(routes, droneIdentifier)

        resultado mustBe expectedResult.asLeft
      }

    }

    "startDeliveryLunches" should {

      "When starts to deliver lunches " +
        "Where: " +
        "- The input file exists with routes (AAAAIAAD, DDAIAD, AAIADAD) " +
        "- The specified motions are allowed " +
        "- The visited places are inbound " +
        "Then it must generate a file report " in {

        val expectedLinesFromReportsFile = List(
          "( -2, 4 ) dirección Norte",
          "( -1, 3 ) dirección Sur",
          "( 0, 0 ) dirección Oeste"
        ) ++
          List(
            "( -2, 4 ) dirección Norte",
            "( -1, 3 ) dirección Sur",
            "( 0, 0 ) dirección Oeste"
          )

        val idDrones = List(DroneIdentifier(id = "test-lunch-service"), DroneIdentifier(id = "test-lunch-service"))

        Await.result(LunchDeliveryService.startDeliveryLunches(idDrones).value.runAsync, Duration.Inf)

        val linesWroteInReports = idDrones.flatMap(idDrone => readLinesFromFileInOutDir(idDrone.id))

        linesWroteInReports mustBe expectedLinesFromReportsFile
      }

      "When starts to deliver lunches " +
        "Where: " +
        "- The input file exists with routes (Foo, Bar, Baz) " +
        "- The specified motions are not allowed " +
        "Then it must not generate a file report for that file " in {

        val idDrones = List(DroneIdentifier(id = "test-lunch-service-bad"))

        Await.result(LunchDeliveryService.startDeliveryLunches(idDrones).value.runAsync, Duration.Inf)

        val linesWroteInReports = idDrones.flatMap(idDrone => readLinesFromFileInOutDir(idDrone.id))

        linesWroteInReports mustBe Nil
      }

      "When starts to deliver lunches " +
        "Where: " +
        "- The input file not exists " +
        "Then it must not generate a file report for that file " in {

        val idDrones = List(DroneIdentifier(id = "not-exists"))

        Await.result(LunchDeliveryService.startDeliveryLunches(idDrones).value.runAsync, Duration.Inf)

        val linesWroteInReports = idDrones.flatMap(idDrone => readLinesFromFileInOutDir(idDrone.id))

        linesWroteInReports mustBe Nil
      }

      "When starts to deliver lunches " +
        "Where: " +
        "- The first input file not exists " +
        "- The second input file exists " +
        "Then it must not generate a file report for the first file, but do for second one " in {

        val idDrones = List( DroneIdentifier( id = "not-exists" ), DroneIdentifier( id = "test-lunch-service" ) )

        val expectedLinesFromReportSecondFile = List(
          "( -2, 4 ) dirección Norte",
          "( -1, 3 ) dirección Sur",
          "( 0, 0 ) dirección Oeste"
        )

        Await.result(LunchDeliveryService.startDeliveryLunches(idDrones).value.runAsync, Duration.Inf)

        val linesWroteInReportFirstFile = readLinesFromFileInOutDir("not-exists")
        val linesWroteInReportSecondFile = readLinesFromFileInOutDir("test-lunch-service")

        linesWroteInReportFirstFile mustBe Nil
        linesWroteInReportSecondFile mustBe expectedLinesFromReportSecondFile
      }
    }
  }
}
