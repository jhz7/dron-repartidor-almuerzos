package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.{DroneIdentifier, Position}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types.{EAST, NORTH}
import org.scalatest.{MustMatchers, WordSpecLike}

class LunchDeliveryServiceTest extends MustMatchers with WordSpecLike {

  "deliverLunches" should {

    "When starts to deliver lunches given a list of routes " +
      "Where the delivery is successfull " +
      "Then it must restur a list with the places visited " in {

      val routes = List("AADA", "ID", "A", "A")
      val droneIdentifier = DroneIdentifier( id = 1 )
      val expectedResult = List(
        Position( 2, 2, EAST ),
        Position( 3, 3, EAST ),
        Position( 4, 3, EAST ),
        Position( 0, 1, NORTH )
      )

      val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

      resultado mustBe expectedResult.asRight
    }

    "When starts to deliver lunches given a list of routes " +
      "Where a provided motion is invalid " +
      "Then it must restur a list with the places visited " in {

      val routes = List("AADA", "ID", "A", "A")
      val droneIdentifier = DroneIdentifier( id = 1 )
      val expectedResult = List(
        Position( 2, 2, EAST ),
        Position( 3, 3, EAST ),
        Position( 4, 3, EAST ),
        Position( 0, 1, NORTH )
      )

      val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

      resultado mustBe expectedResult.asRight
    }
  }
}
