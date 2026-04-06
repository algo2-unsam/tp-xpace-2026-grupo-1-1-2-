package ar.edu.unsam.algo2

import java.time.LocalDate
import java.time.Period

abstract class Naves(
    val nombre: String,
    val ID: Int,
    val fechaFab: LocalDate,
    val velocidadProm: Int,
    val autonomia: Int,
    var consumoBase: Double,
    val capacidad: Int,
    var baseActual: BaseLanzamiento // Agregamos la base donde está estacionada
) {
    var mision: Boolean = false

    fun antiguedad(): Int {
        return Period.between(fechaFab, LocalDate.now()).years
    }

    fun cambiaEstado(): Boolean {
        mision = !mision
        return mision
    }

    open fun cuantoConsume(): Double {
        return consumoBase
    }

    fun consumoTotal(planeta: Planetas): Double {
        return cuantoConsume() * planeta.distTierra
    }

    fun alcanzaPlaneta(planeta: Planetas): Boolean {
        // Multiplicamos por 2 porque la misión es ida y vuelta (duración total)
        return (planeta.distTierra * 2 / velocidadProm) <= autonomia
    }

    fun esModerna(): Boolean {
        return antiguedad() < 5
    }

    // Ahora recibe la cantidad de tripulantes de la misión para validar
    fun capacidadApta(cantidadTripulantes: Int): Boolean {
        return cantidadTripulantes <= capacidad
    }
}

class Sonda(
    nombre: String,
    ID: Int,
    fechaFab: LocalDate,
    velocidadProm: Int,
    autonomia: Int,
    consumoBase: Double,
    baseActual: BaseLanzamiento
) : Naves(nombre, ID, fechaFab, velocidadProm, autonomia, consumoBase, 0, baseActual) {

}

class Transbordador(
    nombre: String,
    ID: Int,
    fechaFab: LocalDate,
    velocidadProm: Int,
    autonomia: Int,
    consumoBase: Double,
    capacidad: Int,
    baseActual: BaseLanzamiento
) : Naves(nombre, ID, fechaFab, velocidadProm, autonomia, consumoBase, capacidad, baseActual) {

}

class Carguero(
    nombre: String,
    ID: Int,
    fechaFab: LocalDate,
    velocidadProm: Int,
    autonomia: Int,
    consumoBase: Double,
    capacidad: Int,
    baseActual: BaseLanzamiento
) : Naves(nombre, ID, fechaFab, velocidadProm, autonomia, consumoBase, capacidad, baseActual) {

    override fun cuantoConsume(): Double {
        return consumoBase * 1.5
    }
}