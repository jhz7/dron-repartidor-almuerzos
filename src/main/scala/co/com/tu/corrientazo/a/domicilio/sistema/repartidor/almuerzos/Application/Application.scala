package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.{CustomEither, CustomValidated}

object Application {

  val lunchAmountPerTravel = 3

  val rangeOfMotionInX: List[Int] = (-10 to 10).toList

  val rangeOfMotionInY: List[Int] = (-10 to 10).toList

  implicit class CastFromValidatedNelToEither[A <: Any]( validatedNel: CustomValidated[A] ) {
    def toCustomEither: CustomEither[A] = validatedNel.fold(
      errors => generateUniqueErrorMessage( errors.toList ).asLeft,
      data => data.asRight
    )
  }

  private def generateUniqueErrorMessage( errors: List[ErrorMessage] ): ErrorMessage =
    ErrorMessage( errors.mkString("- ") )
}