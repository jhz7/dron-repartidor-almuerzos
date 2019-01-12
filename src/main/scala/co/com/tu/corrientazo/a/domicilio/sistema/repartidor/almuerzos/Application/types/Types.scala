package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types

import cats.data.ValidatedNel
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage

object Types {

  type CustomEither[A] = Either[ErrorMessage, A]

  type CustomValidated[A] = ValidatedNel[ErrorMessage, A]
}
