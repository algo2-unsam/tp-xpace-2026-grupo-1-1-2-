package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period
import kotlin.properties.Delegates

abstract class Nave(
        open var nombre:String,
        open val id:Int,
        open val fecha_fabricacion:LocalDate,
        open var velocida_promedio:Double,
        open val autonomia:Double,
        open val consumo:Double
        ){
    var en_mision:Boolean=false
}

class Sonda(
    override var nombre:String,
    override val id:Int,
    override val fecha_fabricacion:LocalDate,
    override var velocida_promedio:Double,
    override val autonomia:Double,
    override val consumo:Double
) : Nave(nombre, id, fecha_fabricacion, velocida_promedio, autonomia, consumo) {

}

class Transbordador (
    override var nombre:String,
    override val id:Int,
    override val fecha_fabricacion:LocalDate,
    override var velocida_promedio:Double,
    override val autonomia:Double,
    override val consumo:Double,
    val capacidad:Int
) : Nave(nombre, id, fecha_fabricacion, velocida_promedio, autonomia, consumo) {
    val tripulantes = mutableSetOf()

    fun cantidad_tripulantes() = tripulantes.size
    fun consumo() : Double =  super.consumo + (super.consumo*0.1)*cantidad_tripulantes()
    fun añadir_tripulante(tripulante:Tripulante){if(tiene_capacidad()) tripulantes.add(tripulante)}
    fun tiene_capacidad() : Boolean = cantidad_tripulantes() < capacidad
    }

class carguero (
    override var nombre:String,
    override val id:Int,
    override val fecha_fabricacion:LocalDate,
    override var velocida_promedio:Double,
    override val autonomia:Double,
    override val consumo:Double,
    val capacidad : Double
) : Nave(nombre, id, fecha_fabricacion, velocida_promedio, autonomia, consumo) {
    val carga = mutableListOf(carga)
    fun tiene_capacidad() : Boolean =
}
