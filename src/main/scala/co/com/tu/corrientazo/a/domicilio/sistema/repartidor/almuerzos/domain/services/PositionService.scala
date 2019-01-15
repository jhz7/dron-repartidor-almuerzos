package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.types.{CustomEither, CustomValidated}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.models.Position

object PositionService {

  def validatePosition( position: Position ): CustomEither[Position] = {
    (
      validateXPosition( position.x ),
      validateYPosition( position.y )
    ).mapN( (_, _) => position ).toCustomEither
  }

  private def validateXPosition( xPosition: Int ): CustomValidated[Int] =
    if( rangeOfMotionInX.contains( xPosition ) )
      xPosition.valid
    else
      ErrorMessage( "La posición en el eje X a la que desea moverse es inválida. " ).invalidNel

  private def validateYPosition( yPosition: Int ): CustomValidated[Int] =
    if( rangeOfMotionInY.contains( yPosition ) )
      yPosition.valid
    else
      ErrorMessage( "La posición en el eje Y a la que desea moverse es inválida. " ).invalidNel
}
