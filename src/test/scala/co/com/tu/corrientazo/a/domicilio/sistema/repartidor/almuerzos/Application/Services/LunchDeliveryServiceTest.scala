package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.DroneIdentifier
import org.scalatest.{MustMatchers, WordSpecLike}

class LunchDeliveryServiceTest extends MustMatchers with WordSpecLike {

  "deliverLunches" should {

    "" in {

      val routes = List("AADA")
      val droneIdentifier = DroneIdentifier( id = 1 )

      val resultado = LunchDeliveryService.deliverLunches( routes, droneIdentifier )

      val x = 1
    }
  }
}
