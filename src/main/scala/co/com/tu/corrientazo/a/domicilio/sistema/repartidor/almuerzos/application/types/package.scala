package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application

import cats.data.{EitherT, ValidatedNel}
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.models.ErrorMessage
import monix.eval.Task

package object types {

  type CustomEither[A] = Either[ErrorMessage, A]

  type CustomEitherT[A] = EitherT[Task, ErrorMessage, A]

  type CustomValidated[A] = ValidatedNel[ErrorMessage, A]
}
