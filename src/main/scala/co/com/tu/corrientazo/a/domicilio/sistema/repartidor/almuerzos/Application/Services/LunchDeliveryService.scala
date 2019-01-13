package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.data.EitherT
import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.Types.{CustomEither, CustomEitherT}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Services.PositionService
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types._
import monix.eval.Task
import monix.reactive.Observable

import scala.annotation.tailrec

object LunchDeliveryService {

  def startDeliveryLunches( idDrones: List[DroneIdentifier] ): CustomEitherT[List[Unit]] = {
    EitherT {
      Observable.fromIterable( idDrones )
        .mapParallelUnordered( parallelism = 20 ){ idDrone => startDeliveryLunchesPerDrone( idDrone ).value }
        .toListL.map( _.sequence )
    }
  }

  private def startDeliveryLunchesPerDrone( idDrone: DroneIdentifier ): CustomEitherT[Unit] = {
    EitherT {
      Task {
        val routes = FileService.readLinesFromFile( idDrone.id )
        deliverLunches( routes, idDrone )
          .map( visitedPlaces => {
            val linesForReport = visitedPlaces.map( generateLineReportFromPosition )
            if ( linesForReport.nonEmpty ) FileService.writeLinesToFile( linesForReport, idDrone.id )
            else ()
          })
      }
    }
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