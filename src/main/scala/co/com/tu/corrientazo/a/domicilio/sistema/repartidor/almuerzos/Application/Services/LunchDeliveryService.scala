package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.Types.CustomEither
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Services.PositionService
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types._

import scala.annotation.tailrec

object LunchDeliveryService {

  def deliverLunches( routes: List[String], droneIdentifier: DroneIdentifier ): CustomEither[Drone] = {

    val initialPosition = Position( x = 0, y = 0, NORTH )
    val drone = Drone( droneIdentifier, initialPosition )

    routes
      .sliding( lunchAmountPerTravel, lunchAmountPerTravel ).toList
      .map( routesPerTravel => deliverLunchesPerTravel( routesPerTravel, drone ) )
      .sequence
      .map( _ => drone )
  }

  private def deliverLunchesPerTravel( routes: List[String], drone: Drone ): CustomEither[Drone] = {
    val reverseRoutes = routes.reverse
    processDeliverLunchesPerTravel( reverseRoutes, drone )
  }

  @tailrec
  private def processDeliverLunchesPerTravel( routes: List[String], drone: Drone ): CustomEither[Drone] = {
    routes match {
      case Nil => drone.asRight
      case x :: xs =>
        deliverLunch( x, drone ) match {
          case Right( newPosition ) => processDeliverLunchesPerTravel( xs, drone.copy( currentPosition = newPosition ) )
          case Left(error)          => error.asLeft
        }
    }
  }

  private def deliverLunch( route: String, drone: Drone ): CustomEither[Position] = {
    val reverseRoute = route.toList.reverse
    processDeliverLunch( reverseRoute, drone )
  }

  @tailrec
  private def processDeliverLunch( route: List[Char], drone: Drone ): CustomEither[Position] =
    route match {
      case Nil => drone.currentPosition.asRight
      case x :: xs =>
        val motion = Motion( x )

        val validatedPosition = for {
          newPosition <- MotionService.makeAMotion( motion, drone.currentPosition )
          _ <- PositionService.validatePosition( newPosition )
        } yield newPosition

        validatedPosition match {
          case Right( newPosition ) => processDeliverLunch( xs, drone.copy( currentPosition = newPosition ) )
          case error @ Left( _ )    => error
        }
    }
}