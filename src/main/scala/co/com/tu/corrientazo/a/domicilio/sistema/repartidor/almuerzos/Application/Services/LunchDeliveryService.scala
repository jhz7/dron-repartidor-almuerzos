package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.Types.CustomEither
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Services.PositionService
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types._

import scala.annotation.tailrec

object LunchDeliveryService {

  def startDeliveryLunches( idDrones: List[DroneIdentifier] ): Unit = {

    val idDrone = DroneIdentifier( id = "01" )
    val routes = FileService.readLinesFromFile( idDrone.id )

    deliverLunches( routes, idDrone )
      .map( visitedPlaces => {
        val linesForReport = visitedPlaces.map( generateLineReportFromPosition )
        FileService.writeLinesToFile( linesForReport, idDrone.id )
      })

    ()
  }

  def deliverLunches( routes: List[String], droneIdentifier: DroneIdentifier ): CustomEither[List[Position]] = {

    val initialPosition = Position( x = 0, y = 0, NORTH )
    val drone = Drone( droneIdentifier, initialPosition )

    routes
      .sliding( lunchAmountPerTravel, lunchAmountPerTravel ).toList
      .map( routesPerTravel => deliverLunchesPerTravel( routesPerTravel, drone ) )
      .sequence
      .map( _.flatten )
  }

  @tailrec
  private def deliverLunchesPerTravel( routes: List[String], drone: Drone, visitedPlaces: List[Position] = Nil ): CustomEither[List[Position]] = {
    routes match {
      case Nil     => visitedPlaces.asRight
      case x :: xs =>
        deliverLunch( x, drone ) match {
          case Right( newPosition ) =>
            val newVisitedPlaces = visitedPlaces :+ newPosition
            deliverLunchesPerTravel( xs, drone.copy( currentPosition = newPosition ), newVisitedPlaces )
          case Left(error)          => error.asLeft
        }
    }
  }

  private def deliverLunch( route: String, drone: Drone ): CustomEither[Position] = {
    val motionList = route.toList
    processDeliveryLunch( motionList, drone )
  }

  @tailrec
  private def processDeliveryLunch( route: List[Char], drone: Drone ): CustomEither[Position] =
    route match {
      case Nil     => drone.currentPosition.asRight
      case x :: xs =>
        val motion = MotionType( x )

        val validatedPosition = for {
          newPosition <- MotionService.makeAMotion( motion, drone.currentPosition )
          _ <- PositionService.validatePosition( newPosition )
        } yield newPosition

        validatedPosition match {
          case Right( newPosition ) => processDeliveryLunch( xs, drone.copy( currentPosition = newPosition ) )
          case error @ Left( _ )    => error
        }
    }

  private def generateLineReportFromPosition( position: Position ): String =
    s"( ${position.x}, ${position.y} ) dirección ${position.orientation.toString}"
}