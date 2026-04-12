package ar.edu.unsam.algo2
import java.time.LocalDate
import kotlin.collections.mutableSetOf

class Mision(
    val nombre: String="Mision 1",
    val descripcion: String = "Viaje a planeta",
    val fechaLanzamiento: LocalDate = LocalDate.of(2027, 1, 1)
){
    val tripulantes = mutableSetOf<Tripulante>()
    var nave: Nave = Transbordador()
    lateinit var baseLanzamiento: BaseLanzamiento
    var planeta: Planeta = Planeta()
    var estado: EstadoMision = EstadoMision.BORRADOR

    fun tripulantesSonAptos(): Boolean = tripulantes.any({it -> !it.esApto()})
    fun mismaBase(): Boolean = tripulantes.any({it->it.baseAsignada.nombre != baseLanzamiento.nombre})
    fun enCurso(): Boolean = estado == EstadoMision.EN_CURSO
    fun misionNoIniciada(): Boolean = estado == EstadoMision.BORRADOR
    fun naveRegistrada(): Boolean = nave.mision.nombre == nombre
    fun duracionEstimada(): Double = planeta.distanciaTierra*365 / nave.velocidadPromedio*2
    fun altoRiesgo(): Boolean = !planeta.esHabitable() && duracionEstimada() > 500

    fun cambiarEstado(nuevoEstado: EstadoMision, tripulanteMision: () -> Unit = {}) {
        if(enCurso()) {
            estado = nuevoEstado
            tripulanteMision()
            tripulantes.forEach({it -> it.desasignarMision()})
            nave.desasignarMision()
        }
    }

    fun completarMision() {
        cambiarEstado(EstadoMision.COMPLETADA, {tripulantes.forEach {completarMision()}})
        planeta.aterrizaje()
    }

    fun fracasarMision() {
        cambiarEstado(EstadoMision.FALLIDA, {tripulantes.forEach{ fracasarMision() }})
    }

    fun cancelarMision() {
        cambiarEstado(EstadoMision.CANCELADA, {if(altoRiesgo()) tripulantes.forEach {cancelarMision()}})
    }

    fun cumpleCondiciones(): Boolean {
        return misionNoIniciada() &&
        nave.puedeAlcanzar(planeta.distanciaTierra) &&
        naveRegistrada() &&
        tripulantesSonAptos() &&
        nave.tieneCapacidad() &&
        mismaBase() &&
        baseLanzamiento.alojaNave(nave)
    }

    fun lanzarMision() {if(cumpleCondiciones()) estado=EstadoMision.EN_CURSO}
}

enum class EstadoMision { BORRADOR, EN_CURSO, COMPLETADA, FALLIDA, CANCELADA }

