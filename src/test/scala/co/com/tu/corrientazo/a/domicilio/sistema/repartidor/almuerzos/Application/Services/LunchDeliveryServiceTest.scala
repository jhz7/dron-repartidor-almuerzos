package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Tools._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.{DroneIdentifier, Position}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types.{EAST, NORTH, SOUTH, WEST}
import org.scalatest.{MustMatchers, WordSpecLike}

class LunchDeliveryServiceTest extends MustMatchers with WordSpecLike {

  "LunchDeliveryService" should {

    "deliverLunches" should {

      "This test is only to show that example given in exercise has a wrong result: " +
        "When starts to deliver lunches given a list of routes (taken from example) " +
        "Where the delivery is successfull " +
        "Then it must resturn a list with the places visited " in {

        val routes = List("AAAAIAAD", "DDAIAD", "AAIADAD" )
        val droneIdentifier = DroneIdentifier( id = "1" )
        val expectedResult = List(
          Position( -2, 4, NORTH ),
          Position( -1, 3, SOUTH ),
          Position( 0, 0, WEST )
        )

        val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

        resultado mustBe expectedResult.asRight
      }

      "When starts to deliver lunches given a list of routes " +
        "Where the delivery is successfull " +
        "Then it must resturn a list with the places visited " in {

        val routes = List("AADA", "IAD", "A", "A")
        val droneIdentifier = DroneIdentifier( id = "1" )
        val expectedResult = List(
          Position( 1, 2, EAST ),
          Position( 1, 3, EAST ),
          Position( 2, 3, EAST ),
          Position( 0, 1, NORTH )
        )

        val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

        resultado mustBe expectedResult.asRight
      }

      "When starts to deliver lunches given a list of routes " +
        "Where a route has an invalid motion (J) " +
        "Then it must resturn a n error " in {

        val routes = List("AAJDA", "IAD")
        val droneIdentifier = DroneIdentifier( id = "1" )
        val expectedResult = ErrorMessage( "Está intentando hacer un movimiento no permitido, por favor revisar las rutas establecidas. " )

        val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

        resultado mustBe expectedResult.asLeft
      }

      "When starts to deliver lunches given a list of routes " +
        "Where a calculated position is out of allowed reange " +
        "Then it must resturn a n error " in {

        val routes = List("AADAAAAAAAAAAA", "IAD")
        val droneIdentifier = DroneIdentifier( id = "1" )
        val expectedResult = ErrorMessage( "La posición en el eje X a la que desea moverse es inválida. " )

        val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

        resultado mustBe expectedResult.asLeft
      }

    }

    "startDeliveryLunches" should {

      "When starts to deliver lunches " +
        "Where: " +
        "- The input file exists with routes (AAAAIAAD, DDAIAD, AAIADAD) " +
        "- The specified motions are allowed " +
        "- The visited places are inbound " +
        "Then it must generate a file report " in{

        val expectedLinesFromReportFile = List(
          "( -2, 4 ) dirección Norte",
          "( -1, 3 ) dirección Sur",
          "( 0, 0 ) dirección Oeste"
        )

        LunchDeliveryService.startDeliveryLunches()

        val linesWroteInReport = readLinesFromFileInOutDir("01")

        linesWroteInReport mustBe expectedLinesFromReportFile
      }
    }
  }
}
