package ar.edu.unsam.algo2
import java.awt.Point

abstract class BaseLanzamiento() {
    open val nombre: String = ""
    open val capacidadMax: Int = 10
    val direccion: Direccion = Direccion()
    val navesEstacionadas = mutableListOf<Nave>()
}

class BaseLanus: BaseLanzamiento() {
    override val nombre = "Base1"
}

class Direccion(){
    open val pais : String = ""
    open val ciudad : String = ""
    open val calle : String = ""
    open val altura : Int = 0
    open val ubiGeo : Point = Point(0, 0)
}