package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types

import cats.data.{EitherT, ValidatedNel}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import monix.eval.Task

object Types {

  type CustomEither[A] = Either[ErrorMessage, A]

  type CustomEitherT[A] = EitherT[Task, ErrorMessage, A]

  type CustomValidated[A] = ValidatedNel[ErrorMessage, A]
}
