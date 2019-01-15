package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.application.models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.models.Position
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.types.NORTH
import org.scalatest.{MustMatchers, WordSpecLike}

class PositionServiceTest extends MustMatchers with WordSpecLike {

  "PositionService" should {

    "validatePosition" should {

      "When starts to validate the drone position " +
        "Where X coordinate is outbound" +
        "Then it must return an error " in {

        val positiontoValidate = Position( 11, 5, NORTH )

        val result = PositionService.validatePosition( positiontoValidate )

        result mustBe ErrorMessage( "La posición en el eje X a la que desea moverse es inválida. " ).asLeft
      }

      "When starts to validate the drone position " +
        "Where Y coordinate is outbound" +
        "Then it must return an error " in {

        val positiontoValidate = Position( 5, -11, NORTH )

        val result = PositionService.validatePosition( positiontoValidate )

        result mustBe ErrorMessage( "La posición en el eje Y a la que desea moverse es inválida. " ).asLeft
      }

      "When starts to validate the drone position " +
        "Where X and Y coordinates are outbound" +
        "Then it must return an error " in {

        val positiontoValidate = Position( 15, -20, NORTH )

        val result = PositionService.validatePosition( positiontoValidate )

        result mustBe ErrorMessage( "La posición en el eje X a la que desea moverse es inválida. - La posición en el eje Y a la que desea moverse es inválida. " ).asLeft
      }

      "When starts to validate the drone position " +
        "Where X and Y coordinates are inbound" +
        "Then it must return an the validated position " in {

        val positiontoValidate = Position( 10, 5, NORTH )

        val result = PositionService.validatePosition( positiontoValidate )

        result mustBe positiontoValidate.asRight
      }
    }
  }

}
