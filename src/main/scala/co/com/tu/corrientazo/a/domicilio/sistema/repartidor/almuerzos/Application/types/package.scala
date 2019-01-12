package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application

import cats.data.ValidatedNel
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage

package object types {

  type CustomEither[A] = Either[ErrorMessage, A]

  type CustomValidated[A] = ValidatedNel[ErrorMessage, A]
}
