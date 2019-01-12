package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.{DroneIdentifier, Position}

import scala.io.{BufferedSource, Source}

object FileService {

  def readRoutesFile( fileName: String ): List[String] = {
    val dir = s"resources/in/$fileName.txt"
    val sourceStream: BufferedSource = Source.fromFile( dir )
    sourceStream.getLines().toList
  }

  def writeFileOfVisitedPlaces( visitedPlaces: List[Position], idDrone: String ) = {
    val fileToWrite = s"resources/out/out$idDrone.txt"
    val linesToWrite = visitedPlaces.map( position => s"( ${position.x}, ${position.y} ) dirección ${position.orientation.toString} \n" )
    ???
  }
}
