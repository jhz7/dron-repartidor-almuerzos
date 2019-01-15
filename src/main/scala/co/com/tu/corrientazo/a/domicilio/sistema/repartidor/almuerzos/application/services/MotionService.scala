package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.types.CustomEither
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.models.Position
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.types._

object MotionService {

  def makeAMotion(motion: MotionType, currentPosition: Position ): CustomEither[Position] =
    motion match {
      case FORWARD => moveForward( currentPosition )
      case RIGHT   => moveToRight( currentPosition )
      case LEFT    => moveToLeft( currentPosition )
      case _       => ErrorMessage( "Está intentando hacer un movimiento no permitido, por favor revisar las rutas establecidas. " ).asLeft
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
