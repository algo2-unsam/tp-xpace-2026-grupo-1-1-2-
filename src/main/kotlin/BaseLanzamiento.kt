package ar.edu.unsam.algo2
import java.awt.Point

abstract class BaseLanzamiento() {
    open val nombre: String = ""
    open val capacidadMax: Int = 10
    val direccion: Direccion = Direccion()
    val navesEstacionadas = mutableListOf<Nave>()
    fun alojaNave(nave: Nave): Boolean = navesEstacionadas.contains(nave)
    fun coordenadasBase(): Point = direccion.coordenadas
}

class Direccion(){
    open val pais : String = ""
    open val ciudad : String = ""
    open val calle : String = ""
    open val altura : Int = 0
    open val coordenadas : Point = Point(0, 0)
}