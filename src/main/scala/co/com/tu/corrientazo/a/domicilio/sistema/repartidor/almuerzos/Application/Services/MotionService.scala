package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.Types.CustomEither
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.Position
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types._

object MotionService {

  def makeAMotion( motion: Motion, currentPosition: Position ): CustomEither[Position] =
    motion match {
      case FORWARD => moveForward( currentPosition )
      case RIGHT   => moveToRight( currentPosition )
      case LEFT    => moveToLeft( currentPosition )
      case _       => ErrorMessage( "El movimiento no está permitido" ).asLeft
    }

  private def moveToLeft( position: Position ): CustomEither[Position] =
    position.orientation match {
      case NORTH => Position( position.x - 1, position.y, WEST ).asRight
      case WEST  => Position( position.x, position.y - 1, SOUTH ).asRight
      case SOUTH => Position( position.x + 1, position.y, EAST ).asRight
      case EAST  => Position( position.x, position.y + 1, NORTH ).asRight
      case _     => ErrorMessage("La orientación actual para realizar un giro a la izquierda no está definida").asLeft
    }

  private def moveToRight( position: Position ): CustomEither[Position] =
    position.orientation match {
      case NORTH => Position( position.x + 1, position.y, EAST ).asRight
      case EAST  => Position( position.x, position.y - 1, SOUTH ).asRight
      case SOUTH => Position( position.x - 1, position.y, WEST ).asRight
      case WEST  => Position( position.x, position.y + 1, NORTH ).asRight
      case _     => ErrorMessage("La orientación actual para realizar un giro a la derecha no está definida").asLeft
    }

  private def moveForward( position: Position ): CustomEither[Position] =
    position.orientation match {
      case NORTH => Position( position.x, position.y + 1, NORTH ).asRight
      case EAST  => Position( position.x + 1, position.y, EAST ).asRight
      case SOUTH => Position( position.x, position.y - 1, SOUTH ).asRight
      case WEST  => Position( position.x - 1, position.y, WEST ).asRight
      case _     => ErrorMessage("La orientación no está definida y por lo tanto, no es posible mover el dron hacia adelante").asLeft
    }
}
