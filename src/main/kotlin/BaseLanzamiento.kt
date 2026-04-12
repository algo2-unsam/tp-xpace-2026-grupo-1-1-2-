package ar.edu.unsam.algo2
import org.uqbar.geodds.Point

class BaseLanzamiento(
    val nombre: String = "",
    val direccion: Direccion = Direccion(),
    val capacidadMax: Int = 10,
){
    val navesEstacionadas = mutableListOf<Nave>()
}

class Direccion(
    val pais: String = "",
    val ciudad: String = "",
    val calle: String = "",
    val altura: Int = 0,
    val ubiGeo: Point = Point(0.0, 0.0)
)
{}