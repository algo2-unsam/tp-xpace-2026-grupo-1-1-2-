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
    val planetaAsig: Planetas,
    val baseAsignada: BaseLanzamiento
) {

    var estado: EstadoMision = EstadoMision.BORRADOR


    fun duracion(): Int {
        return (planetaAsig.distTierra * 365 / naveAsig.velocidadProm) * 2
    }

    fun altoRiesgo(): Boolean {
        return (!planetaAsig.tempIdeal()) &&
                (!planetaAsig.gravSoportable()) &&
                (duracion() > 500)
    }

    fun puedeLanzarse(): Boolean {
        return estado == EstadoMision.BORRADOR &&
                nombre.isNotBlank() &&
                fechaLanz.isAfter(LocalDate.now()) &&
                naveAsig.alcanzaPlaneta(planetaAsig) &&
                validarTripulacion() &&
                validarBaseYNave()
    }

    fun validarBaseYNave(): Boolean {
        val naveEnBase = naveAsig.baseActual == this.baseAsignada

        // 2. Si hay tripulantes, todos deben pertenecer a la base de la misión
        val tripulantesEnBase = tripulanteAsig.all { it.baseAsignada == this.baseAsignada }

        return naveEnBase && tripulantesEnBase
    }

    fun validarTripulacion(): Boolean {
        val todosAptos = tripulanteAsig.all { it.esApto(this) }
        val capacidadOk = naveAsig.capacidadApta(tripulanteAsig.size)

        return todosAptos && capacidadOk
    }

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
            if (altoRiesgo()) {
                tripulanteAsig.forEach { it.sumaMisionParcial() }
            }
            liberarNaveyTripulantes()
        }
    }

    private fun liberarNaveyTripulantes() {
            naveAsig.mision = false
            tripulanteAsig.forEach { it.misionActual = null}
    }
}
