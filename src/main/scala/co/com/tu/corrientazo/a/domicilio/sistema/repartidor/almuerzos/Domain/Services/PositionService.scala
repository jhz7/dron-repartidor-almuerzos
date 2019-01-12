package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Services

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.{CustomEither, CustomValidated}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.Position
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Application._
import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage

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
    if( rangeOfMotionInX.contains( yPosition ) )
      yPosition.valid
    else
      ErrorMessage( "La posición en el eje Y a la que desea moverse es inválida. " ).invalidNel
}
