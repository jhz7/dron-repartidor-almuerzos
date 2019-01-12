package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.{DroneIdentifier, Position}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types.{EAST, NORTH, SOUTH, WEST}
import org.scalatest.{MustMatchers, WordSpecLike}

class LunchDeliveryServiceTest extends MustMatchers with WordSpecLike {

  "LunchDeliveryService" should {

    "deliverLunches" should {

      "When starts to deliver lunches given a list of routes " +
        "Where a provided motion is invalid " +
        "Then it must restur a list with the places visited " in {

        val routes = List("AAAAIAAD", "DDAIAD", "AAIADAD" )
        val droneIdentifier = DroneIdentifier( id = 1 )
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
        "Then it must restur a list with the places visited " in {

        val routes = List("AADA", "IAD", "A", "A")
        val droneIdentifier = DroneIdentifier( id = 1 )
        val expectedResult = List(
          Position( 1, 2, EAST ),
          Position( 1, 3, EAST ),
          Position( 2, 3, EAST ),
          Position( 0, 1, NORTH )
        )

        val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

        resultado mustBe expectedResult.asRight
      }

    }
  }
}
