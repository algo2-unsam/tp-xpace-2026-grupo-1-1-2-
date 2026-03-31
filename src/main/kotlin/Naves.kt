package ar.edu.unsam.algo2

import java.time.LocalDate
import java.time.Period


abstract class Naves (
    val nombre: String,
    val ID: Int,
    val fechaFab: LocalDate,
    val velocidadProm: Int,
    val autonomia: Int,
    var consumoBase: Double,
    val capacidad : Int,
    val tripulantes:
){
    var mision : Boolean = false

    fun antiguedad(): Int {
        return Period.between(fechaFab, LocalDate.now()).years
    }
    fun cambiaEstado(): Boolean {
        mision = !mision
        return mision
    }
    open fun cuantoConsume(): Double{
        return consumoBase
    }
    fun consumoTotal(planeta: Planetas): Double {
        return cuantoConsume() * planeta.distTierra
    }
    fun alcanzaPlaneta(planeta: Planetas): Boolean{
        return ((planeta.distTierra * 365 / velocidadProm) < autonomia)
    }
    fun esModerna(): Boolean{
        return antiguedad() < 5
    }
    fun capacidadApta(): Boolean{
        if ((capacidad >= tripulantes) && (capacidad >= 1)){return true}
        else{return false}
    }
}