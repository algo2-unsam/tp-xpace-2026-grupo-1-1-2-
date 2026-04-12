package ar.edu.unsam.algo2
import java.time.LocalDate

enum class EstadoMision { BORRADOR, EN_CURSO, COMPLETADA, FALLIDA, CANCELADA }

class Mision(
    val nombre: String="Mision1",
    val descripcion: String="Mision1",
    val fechaLanz: LocalDate = LocalDate.of(2007, 1, 1),
    var nave: Nave= Transbordador()

) {
    var estado: EstadoMision = EstadoMision.BORRADOR
    var tripulantes: MutableList<Tripulante> = mutableListOf()
    var planeta: Planeta= Planeta()

    fun mismaBaseYNaveEnBase(): Boolean =
        if (tripulantes.isNotEmpty()) {
            val baseComun = tripulantes.first().baseAsignada
            tripulantes.all { it.baseAsignada == baseComun } &&
                    baseComun.navesEstacionadas.contains(nave)
        } else true

    fun preLanzamiento(): Boolean =
        estado == EstadoMision.BORRADOR && nave.puedeAlcanzar(planeta) && tripulantes.all { it.esAptoPara(this) } && capacidadValida() && mismaBaseYNaveEnBase()

    fun lanzar() {
        if (preLanzamiento()){
            estado = EstadoMision.EN_CURSO
            nave.enMision = true
            tripulantes.forEach { it.misionActual = this }
        }
    }

    fun completar(){
        if (estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.COMPLETADA
            planeta.aterrizado = true
            tripulantes.forEach {
                it.misExitosa += 1
                it.misionActual = null
            }
            nave.enMision = false
        }
    }

    fun fallar() {
        estado = EstadoMision.FALLIDA
        tripulantes.forEach { it.misFallidas += 1 }
        nave.enMision = false
    }
    fun cancelar() {
        if (estado == EstadoMision.EN_CURSO && altoRiesgo()){
            estado = EstadoMision.CANCELADA
            nave.enMision = false
            tripulantes.forEach {it.misParcial+=1}
        }
    }

    fun capacidadValida(): Boolean {
        return if (nave is Transbordador) {
            tripulantes.size <= (nave as Transbordador).capacidadMax
        } else true
    }

    fun duracionEstimada(): Double = (planeta.distTierra * 365 / nave.velocidadProm) * 2

    fun altoRiesgo(): Boolean = !planeta.esHabitable() && duracionEstimada() > 500
}