package co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Models

import co.com.tu.corrientazo.a.domicilio.sistema.repartidor.almuerzos.Domain.Types.OrientationType

final case class Drone( id: DroneIdentifier, currentPosition: Position )

final case class DroneIdentifier( id: String )

final case class Position( x: Int, y: Int, orientation: OrientationType )