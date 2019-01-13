package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services.FileService
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services.LunchDeliveryService.deliverLunches
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.DroneIdentifier


object Main extends App {

  def startDeliveryLunches(): Unit = {

    val idDrone = DroneIdentifier( id = "01" )
    val routes = FileService.readLinesFromFile( idDrone.id )

    deliverLunches( routes, idDrone )
      .map( visitedPlaces => {
        val linesForReport = visitedPlaces.map( position => s"( ${position.x}, ${position.y} ) dirección ${position.orientation.toString}" )
        FileService.writeLinesToFile( linesForReport, idDrone.id )
      })
  }

  startDeliveryLunches()
}
