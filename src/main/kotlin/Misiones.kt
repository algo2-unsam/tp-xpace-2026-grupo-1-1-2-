package ar.edu.unsam.algo2

import java.time.LocalDate
import java.time.Period


enum class EstadoMision { BORRADOR, EN_CURSO, COMPLETADA, FALLIDA, CANCELADA }

class Misiones(
    val nombre: String,
    val descripcion: String,
    val fechaLanz: LocalDate,
    val naveAsig: Naves,
    val tripulanteAsig: MutableList<Tripulante> = mutableListOf(),
    val planetaAsig: Planetas
) {

    var estado: EstadoMision = EstadoMision.BORRADOR

    fun duracion(): Int {
        return (planetaAsig.distTierra * 365 / naveAsig.velocidadProm) * 2
    }

    fun altoRiesgo(planeta: Planetas, nave: Naves): Boolean {
        return ((!planeta.tempIdeal()) && (!planeta.gravSoportable()) && (duracion(planeta, nave) > 500))
    }

    fun puedeLanzarse(): Boolean {
        return estado == EstadoMision.BORRADOR &&
                naveAsig.alcanzaPlaneta(planetaAsig) &&
                validarTripulacion() &&
                validarBaseYNave()
    }

    fun validarBaseYNave(): Boolean {}

    fun validarTripulacion(): Boolean {}

    fun lanzar() {
        if (puedeLanzarse()) {
            estado = EstadoMision.EN_CURSO
            naveAsig.mision = true
            tripulanteAsig.forEach { it.misionActual = this }
        }
    }

    fun completar() {
        if (estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.COMPLETADA
            liberarNaveyTripulantes()
            tripulanteAsig.forEach { it.sumaMisionExitosa() }
        }
    }

    fun fallado() {
        if (estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.FALLIDA
            liberarNaveyTripulantes()
            tripulanteAsig.forEach { it.sumaMisionFallida() }
        }
    }

    fun cancelado() {
        if (estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.CANCELADA
            if (altoRiesgo(planetaAsig, naveAsig)) {
                tripulanteAsig.forEach { it.sumaMisionParcial() }
            }
            liberarNaveyTripulantes()
        }
    }

    private fun liberarNaveyTripulantes() {
        if (estado == EstadoMision.EN_CURSO) {
            naveAsig.mision = false
            tripulanteAsig.forEach { it.misionActual = null }
        }
    }
}
