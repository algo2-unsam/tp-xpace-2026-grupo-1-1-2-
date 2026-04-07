package ar.edu.unsam.algo2

import java.time.LocalDate

class Mision(
    val nombre: String="Mision1",
    val descripcion: String="Mision1",
    val fechaLanz: LocalDate = LocalDate.of(2027, 1, 1)
) {
    var tripulantes: MutableList<Tripulante> = mutableListOf()
    var nave: Nave= Transbordador()
    //var planeta: Planeta= Pluton()
    var estado: EstadoMision = EstadoMision.BORRADOR

    fun lanzar(): Boolean {
        val mismaBaseYNaveEnBase = if (tripulantes.isNotEmpty()) {
            val baseComun = tripulantes.first().baseAsignada
            tripulantes.all { it.baseAsignada == baseComun } && baseComun.navesEstacionadas.contains(nave)
        } else true

        val condiciones = estado == EstadoMision.BORRADOR &&
                nave.puedeAlcanzar(planeta) &&
                tripulantes.all { it.esAptoPara(this) } &&
                capacidadValida() &&
                mismaBaseYNaveEnBase

        if (condiciones) {
            estado = EstadoMision.EN_CURSO
            nave.enMision = true
            tripulantes.forEach { it.misionActual = this }
            return true
        }
        return false
    }
    fun completar(): Boolean {
        if (estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.COMPLETADA
            planeta.aterrizado = true
            tripulantes.forEach {
                it.misExitosa += 1
                it.misionActual = null // Liberamos al tripulante
            }
            nave.enMision = false
            return true
        }
        return false
    }

    fun fallar(): Boolean {
        estado = EstadoMision.FALLIDA
        tripulantes.forEach {it.misFallidas+=1}
        nave.enMision = false
        return true
    }
    fun cancelar(): Boolean {
        if (estado == EstadoMision.EN_CURSO && altoRiesgo()){
            estado = EstadoMision.CANCELADA
            nave.enMision = false
            tripulantes.forEach {it.misParcial+=1}
            return true
        }
        return false
    }