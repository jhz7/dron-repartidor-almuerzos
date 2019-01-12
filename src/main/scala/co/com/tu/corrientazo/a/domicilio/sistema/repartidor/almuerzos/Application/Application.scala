package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.types.Types.{CustomEither, CustomValidated}

package object Application {

  val lunchAmountPerTravel = 3

  val rangeOfMotionInX: List[Int] = (-10 to 10).toList

  val rangeOfMotionInY: List[Int] = (-10 to 10).toList

  implicit class CastFromValidatedNelToEither[A <: Any]( validatedNel: CustomValidated[A] ) {
    def toCustomEither: CustomEither[A] = validatedNel.fold(
      errors => generateUniqueErrorMessage( errors.toList ).asLeft,
      data => data.asRight
    )
  }

  implicit class EitherConvert[A, B <: Any]( val e: Seq[Either[A, B]] ) extends AnyVal {
    def sequence: Either[A, List[B]] =
      e.foldRight( Right( Nil ): Either[A, List[B]] ) {
        ( e, acc ) => for ( xs <- acc.right; x <- e.right ) yield x :: xs
      }
  }

  private def generateUniqueErrorMessage( errors: List[ErrorMessage] ): ErrorMessage = {
    val message = errors.map( _.message ).mkString( "- " )
    ErrorMessage( message )
  }
}