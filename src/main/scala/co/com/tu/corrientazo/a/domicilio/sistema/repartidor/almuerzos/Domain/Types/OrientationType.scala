package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types

sealed trait OrientationType

case object NORTH extends OrientationType {
  override def toString: String = "N"
}

case object SOUTH extends OrientationType {
  override def toString: String = "S"
}

case object EAST extends OrientationType {
  override def toString: String = "E"
}

case object WEST extends OrientationType {
  override def toString: String = "W"
}
