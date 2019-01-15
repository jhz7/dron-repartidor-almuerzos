package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.services.LunchDeliveryService
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.models.DroneIdentifier

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  val idDrones = List(
    DroneIdentifier( id = "01" ),
    DroneIdentifier( id = "02" ),
    DroneIdentifier( id = "03" ),
    DroneIdentifier( id = "04" ),
    DroneIdentifier( id = "05" ),
    DroneIdentifier( id = "06" ),
    DroneIdentifier( id = "07" ),
    DroneIdentifier( id = "08" ),
    DroneIdentifier( id = "09" ),
    DroneIdentifier( id = "10" ),
    DroneIdentifier( id = "11" ),
    DroneIdentifier( id = "12" ),
    DroneIdentifier( id = "13" ),
    DroneIdentifier( id = "14" ),
    DroneIdentifier( id = "15" ),
    DroneIdentifier( id = "16" ),
    DroneIdentifier( id = "17" ),
    DroneIdentifier( id = "18" ),
    DroneIdentifier( id = "19" ),
    DroneIdentifier( id = "20" )
  )

  val future = LunchDeliveryService.startDeliveryLunches( idDrones ).value.runAsync

  Await.result( future, Duration.Inf )
}
