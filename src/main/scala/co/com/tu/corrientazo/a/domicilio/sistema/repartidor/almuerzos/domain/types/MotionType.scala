package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.types

sealed trait MotionType

object MotionType {
  def apply( motion: Char ): MotionType =
    motion match {
      case 'A' => FORWARD
      case 'I' => LEFT
      case 'D' => RIGHT
      case _   => INVALID_MOTION
    }
}

case object FORWARD extends MotionType {
  override def toString: String = "A"
}

case object LEFT extends MotionType {
  override def toString: String = "I"
}

case object RIGHT extends MotionType {
  override def toString: String = "D"
}

case object INVALID_MOTION extends MotionType {
  override def toString: String = "InvalidMotion"
}
