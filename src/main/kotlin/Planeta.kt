package ar.edu.unsam.algo2
import java.time.LocalDate

class Planeta(
    val nombre: String = "Pluton",
    var temperaturaMedia: Double = 63.0,
    val gravedad: Double = 3.71,
    var radiacion: Double = 25.0,
    var hayAguaLiquida: Boolean = false,
    var toxicidad: Double = 95.0,
    var actividadTectonica: Double = 0.0,
    val tamanio: Double = 25.0,
    val fechaDescubrimiento: LocalDate = LocalDate.of(1990, 1, 1),
    val distanciaTierra: Double = 2.0,
    var fue_aterrizado: Boolean = false
){
    // METODOS
    fun toxicidadBaja(): Boolean = toxicidad < 30
    fun radiacion_baja(): Boolean = radiacion < 40
    fun tempIdeal(): Boolean = temperaturaMedia in 0.0..40.0
    fun gravedadSoportable() : Boolean = gravedad in 0.0..40.0
    fun es_habitable():Boolean = tempIdeal() && gravedadSoportable() && hayAguaLiquida && toxicidadBaja() && radiacion_baja()
    fun indicePeligrosidad():Double = (radiacion+toxicidad+actividadTectonica)/3
    fun es_explorable():Boolean = !es_habitable() && indicePeligrosidad()<60
    fun aterrizaje() {fue_aterrizado = !fue_aterrizado}
}