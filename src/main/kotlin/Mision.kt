package ar.edu.unsam.algo2
import java.time.LocalDate

class Mision(
    val nombre: String="Mision 1",
    val descripcion: String = "Viaje a planeta",
    val fechaLanzamiento: LocalDate = LocalDate.of(2027, 1, 1)
){
    val tripulantes = mutableSetOf<Tripulante>()
    var nave: Nave = Transbordador()
    var planeta: Planeta = Planeta()
    var estado: EstadoMision = EstadoMision.BORRADOR

    fun completar() {
        if(estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.COMPLETADA
            planeta.aterrizaje()
            tripulantes.forEach() {it.completar_mision()}
            nave.liberarNave()
        }
    }
}

enum class EstadoMision { BORRADOR, EN_CURSO, COMPLETADA, FALLIDA, CANCELADA }

/*
class Mision(
    var nombre:String,
    var descripcion:String,
    var fecha_lanzamiento: LocalDate,
    var en_curso : Boolean = false
    ){
    lateinit var planeta:Planeta
    lateinit var nave:Nave
    val tripulantes = mutableSetOf<Tripulante>()

    fun duracion_estimada():Double = return planeta.distancia_tierra*365/nave.velocidad_promedio*2
    fun consumo_total() : Double = nave.consumo * planeta.distancia_tierra
    fun tripulantes_son_aptos() : Boolean = tripulantes.any(predicate={it -> !it.es_apto()})
    fun puede_lanzar() : Boolean = mision_en_borrador() && nave.puede_alcanzar(planeta.distancia_tierra) && tripulantes_son_aptos() && nave.es_apta()
    fun alto_riego() : Boolean = !planeta.es_habitable() && duracion_estimada() > 500
    fun mision_en_borrador() : Boolean = estado == Estados.BORRADOR
    fun completar_misiones_tripulantes() {tripulantes.forEach({it -> it.completar_mision()})}
    fun fracasar_misiones_tripulantes() {tripulantes.forEach({it -> it.fallar_mision()})}
    fun cancelar_misiones_tripulantes() {tripulantes.forEach({it -> it.cancelar_mision()})}

    var estado = Estados.BORRADORs

    fun iniciar() {if(estado == Estados.BORRADOR) estado = Estados.EN_CURSO}

    fun completar() {
        if (estado == Estados.EN_CURSO) Estados.COMPLETADA
        completar_misiones_tripulantes()
        planeta.aterrizaje()
    }

    fun fracasar() {
        estado = Estados.FALLIDA
    }

    fun cancelar() {
        estado = Estados.CANCELADA
        if(alto_riego()){
            cancelar_misiones_tripulantes()
        }
    }
}

public enum class Estados {
    BORRADOR, COMPLETADA, CANCELADA, EN_CURSO, FALLIDA
}
*/

/*
    Implementar estados de Mision
    Implementar metodo puede_lanzar()
 */