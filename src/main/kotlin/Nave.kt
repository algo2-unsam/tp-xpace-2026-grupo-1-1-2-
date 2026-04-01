package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates

abstract class Nave(
        open var nombre:String,
        open val id:Int,
        open val fecha_fabricacion:LocalDate,
        open var velocidad_promedio:Double,
        open val autonomia:Double,
        open val consumo:Double
        ){
    var en_mision:Boolean=false
    fun es_moderna() : Boolean = antiguedad() < 5
    fun antiguedad() : Long = ChronoUnit.YEARS.between(fecha_fabricacion, LocalDate.now())
}

class Sonda(
    override var nombre:String,
    override val id:Int,
    override val fecha_fabricacion:LocalDate,
    override var velocidad_promedio:Double,
    override val autonomia:Double,
    override val consumo:Double
) : Nave(nombre, id, fecha_fabricacion, velocidad_promedio, autonomia, consumo) {

}

class Transbordador (
    override var nombre:String,
    override val id:Int,
    override val fecha_fabricacion:LocalDate,
    override var velocidad_promedio:Double,
    override val autonomia:Double,
    override val consumo:Double,
    val capacidad:Int
) : Nave(nombre, id, fecha_fabricacion, velocidad_promedio, autonomia, consumo) {
    val tripulantes = mutableSetOf<Tripulante>()

    fun carga() = tripulantes.size
    fun añadir(tripulante:Tripulante) {if(tiene_capacidad()) tripulantes.add(tripulante)}
    fun tiene_capacidad() : Boolean = carga() < capacidad
    fun consumo() : Double =  super.consumo + (super.consumo*0.1)*carga()
    fun es_apto() : Boolean = TODO()
    }

class carguero (
    override var nombre:String,
    override val id:Int,
    override val fecha_fabricacion:LocalDate,
    override var velocidad_promedio:Double,
    override val autonomia:Double,
    override val consumo:Double,
    val capacidad : Double,
    var carga : Double = 0.0
) : Nave(nombre, id, fecha_fabricacion, velocidad_promedio, autonomia, consumo) {
    fun añadir(_carga:Double) {if(tiene_capacidad()) carga += _carga}
    fun tiene_capacidad() : Boolean = carga < capacidad
    fun consumo() : Double = (super.consumo + super.consumo * 0.05 * carga) * bonus_antiguedad()
    fun bonus_antiguedad() : Double = if(antiguedad()>10) 1.2 else 1.0
}

/*
 Realizar metodo es_apto en Transbordador (requiere Base Lanzamiento)
 Realizar implementacion de tripulantes en Sonda
 Ver tema interfaz
*/
