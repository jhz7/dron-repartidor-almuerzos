package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.Types.CustomEither
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.Position
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types._

object MotionService {

  def makeAMotion(motion: MotionType, currentPosition: Position ): CustomEither[Position] =
    motion match {
      case FORWARD => moveForward( currentPosition )
      case RIGHT   => moveToRight( currentPosition )
      case LEFT    => moveToLeft( currentPosition )
      case _       => ErrorMessage( "El movimiento no está permitido" ).asLeft
    }

  private def moveToLeft( position: Position ): CustomEither[Position] =
    position.orientation match {
      case NORTH => Position( position.x, position.y, WEST ).asRight
      case WEST  => Position( position.x, position.y, SOUTH ).asRight
      case SOUTH => Position( position.x, position.y, EAST ).asRight
      case EAST  => Position( position.x, position.y, NORTH ).asRight
    }

  private def moveToRight( position: Position ): CustomEither[Position] =
    position.orientation match {
      case NORTH => Position( position.x, position.y, EAST ).asRight
      case EAST  => Position( position.x, position.y, SOUTH ).asRight
      case SOUTH => Position( position.x, position.y, WEST ).asRight
      case WEST  => Position( position.x, position.y, NORTH ).asRight
    }

  private def moveForward( position: Position ): CustomEither[Position] =
    position.orientation match {
      case NORTH => Position( position.x, position.y + 1, NORTH ).asRight
      case EAST  => Position( position.x + 1, position.y, EAST ).asRight
      case SOUTH => Position( position.x, position.y - 1, SOUTH ).asRight
      case WEST  => Position( position.x - 1, position.y, WEST ).asRight
    }
}
