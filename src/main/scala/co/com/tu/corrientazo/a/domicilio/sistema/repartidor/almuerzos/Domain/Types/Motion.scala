package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types

sealed trait Motion

object Motion {
  def apply( motion: Char ): Motion =
    motion match {
      case 'A' => FORWARD
      case 'I' => LEFT
      case 'D' => RIGHT
      case _   => INVALID_MOTION
    }
}

case object FORWARD extends Motion {
  override def toString: String = "A"
}

case object LEFT extends Motion {
  override def toString: String = "I"
}

case object RIGHT extends Motion {
  override def toString: String = "D"
}

case object INVALID_MOTION extends Motion {
  override def toString: String = "InvalidMotion"
}
