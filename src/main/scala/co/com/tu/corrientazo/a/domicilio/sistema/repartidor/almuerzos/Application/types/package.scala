package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage

package object types {

  type CustomEither[A] = Either[ErrorMessage, A]
}
