package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Services

import cats.implicits._
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Application.Models.ErrorMessage
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models.Position
import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types._
import org.scalatest.{MustMatchers, WordSpecLike}

class MotionServiceTest extends MustMatchers with WordSpecLike {

  "makeAMotion" should {

    "When makes a motion " +
      "Where: " +
      "- The required motion is to forward and " +
      "- The current orientation is North " +
      "Then it must return a new position with the same orientation and Y coordinate sums one" in {

      val requiredMotion = Motion( 'A' )
      val currentPosition = Position( x = 1, y = 5, NORTH )
      val expectedPosition = Position( x = 1, y = 6, NORTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to forward and " +
      "- The current orientation is East " +
      "Then it must return a new position with the same orientation and X coordinate sums one" in {

      val requiredMotion = Motion( 'A' )
      val currentPosition = Position( x = 1, y = 5, EAST )
      val expectedPosition = Position( x = 2, y = 5, EAST )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to forward and " +
      "- The current orientation is South " +
      "Then it must return a new position with the same orientation and Y coordinate subtracts one" in {

      val requiredMotion = Motion( 'A' )
      val currentPosition = Position( x = 1, y = 5, SOUTH )
      val expectedPosition = Position( x = 1, y = 4, SOUTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to forward and " +
      "- The current orientation is West " +
      "Then it must return a new position with the same orientation and X coordinate subtracts one" in {

      val requiredMotion = Motion( 'A' )
      val currentPosition = Position( x = 1, y = 5, WEST )
      val expectedPosition = Position( x = 0, y = 5, WEST )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to forward and " +
      "- The current orientation is invalid " +
      "Then it must return an Error " in {

      val requiredMotion = Motion( 'A' )
      val currentPosition = Position( x = 1, y = 5, INVALID_ORIENTATION )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe ErrorMessage("La orientación no está definida y por lo tanto, no es posible mover el dron hacia adelante").asLeft
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to right and " +
      "- The current orientation is North " +
      "Then it must return a new position with East orientation" in {

      val requiredMotion = Motion( 'D' )
      val currentPosition = Position( x = 2, y = 5, NORTH )
      val expectedPosition = Position( x = 2, y = 5, EAST )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to right and " +
      "- The current orientation is East " +
      "Then it must return a new position with South orientation" in {

      val requiredMotion = Motion( 'D' )
      val currentPosition = Position( x = 1, y = 5, EAST )
      val expectedPosition = Position( x = 1, y = 5, SOUTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to right and " +
      "- The current orientation is South " +
      "Then it must return a new position with West orientation" in {

      val requiredMotion = Motion( 'D' )
      val currentPosition = Position( x = 1, y = 5, SOUTH )
      val expectedPosition = Position( x = 1, y = 5, WEST )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to right and " +
      "- The current orientation is West " +
      "Then it must return a new position with North orientation" in {

      val requiredMotion = Motion( 'D' )
      val currentPosition = Position( x = 1, y = 5, WEST )
      val expectedPosition = Position( x = 1, y = 5, NORTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to right and " +
      "- The current orientation is invalid " +
      "Then it must return an Error " in {

      val requiredMotion = Motion( 'D' )
      val currentPosition = Position( x = 1, y = 5, INVALID_ORIENTATION )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe ErrorMessage("La orientación actual para realizar un giro a la derecha no está definida").asLeft
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to left and " +
      "- The current orientation is North " +
      "Then it must return a new position with West orientation" in {

      val requiredMotion = Motion( 'I' )
      val currentPosition = Position( x = 1, y = 5, NORTH )
      val expectedPosition = Position( x = 1, y = 5, WEST )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to left and " +
      "- The current orientation is West " +
      "Then it must return a new position with South orientation" in {

      val requiredMotion = Motion( 'I' )
      val currentPosition = Position( x = 1, y = 5, WEST )
      val expectedPosition = Position( x = 1, y = 5, SOUTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to left and " +
      "- The current orientation is South " +
      "Then it must return a new position with East orientation" in {

      val requiredMotion = Motion( 'I' )
      val currentPosition = Position( x = 1, y = 5, SOUTH )
      val expectedPosition = Position( x = 1, y = 5, EAST )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to left and " +
      "- The current orientation is East " +
      "Then it must return a new position with North orientation" in {

      val requiredMotion = Motion( 'I' )
      val currentPosition = Position( x = 1, y = 5, EAST )
      val expectedPosition = Position( x = 1, y = 5, NORTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe expectedPosition.asRight
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is to left and " +
      "- The current orientation is invalid " +
      "Then it must return an Error " in {

      val requiredMotion = Motion( 'I' )
      val currentPosition = Position( x = 1, y = 5, INVALID_ORIENTATION )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe ErrorMessage("La orientación actual para realizar un giro a la izquierda no está definida").asLeft
    }

    "When makes a motion " +
      "Where: " +
      "- The required motion is invalid and " +
      "Then it must return an Error " in {

      val requiredMotion = Motion( 'K' )
      val currentPosition = Position( x = 1, y = 5, NORTH )

      val result = MotionService.makeAMotion( requiredMotion, currentPosition )

      result mustBe ErrorMessage( "El movimiento no está permitido" ).asLeft
    }
  }
}
