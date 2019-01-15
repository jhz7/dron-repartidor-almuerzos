package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.domain.types

sealed trait OrientationType

case object NORTH extends OrientationType {
  override def toString: String = "Norte"
}

case object SOUTH extends OrientationType {
  override def toString: String = "Sur"
}

case object EAST extends OrientationType {
  override def toString: String = "Este"
}

case object WEST extends OrientationType {
  override def toString: String = "Oeste"
}
