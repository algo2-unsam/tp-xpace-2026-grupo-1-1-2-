package ar.edu.unsam.algo2

import java.time.LocalDate

class Planeta(
    val nombre: String,
    var temperatura_media: Double,
    val gravedad: Double,
    var radiacion: Double,
    var hay_agua_liquida: Boolean,
    var toxicidad: Double,
    var actividad_tectonica: Double,
    val tamanio: Double,
    val fecha_descubrimiento: LocalDate?,
    val distancia_tierra: Double,
    var fue_aterrizado: Boolean
){
    fun temp_ideal(): Boolean = (temperatura_media > 0 && temperatura_media < 40)
    fun grav_soportable(): Boolean = gravedad > 3 && gravedad < 15
    fun toxicidad_baja(): Boolean = toxicidad < 30
    fun radiacion_baja(): Boolean = radiacion < 40
    fun es_habitable():Boolean = temp_ideal() && grav_soportable() && hay_agua_liquida && toxicidad_baja() && radiacion_baja()
    fun indice_peligrosidad():Double = (radiacion+toxicidad+actividad_tectonica)/3
    fun es_explorable():Boolean = !es_habitable() && indice_peligrosidad()<60
}