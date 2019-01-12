package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types

sealed trait Orientation

object Orientation {
  def apply( orientation: String ): Orientation =
    orientation match {
      case "N" => NORTH
      case "S" => SOUTH
      case "E" => EAST
      case "W" => WEST
      case _   => INVALID_ORIENTATION
    }
}

case object NORTH extends Orientation {
  override def toString: String = "N"
}

case object SOUTH extends Orientation {
  override def toString: String = "S"
}

case object EAST extends Orientation {
  override def toString: String = "E"
}

case object WEST extends Orientation {
  override def toString: String = "W"
}

case object INVALID_ORIENTATION extends Orientation {
  override def toString: String = "Undefined"
}