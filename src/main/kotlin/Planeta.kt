package ar.edu.unsam.algo2
import java.time.LocalDate

class Planeta(
    val nombre: String = "",
    val temperatura: Int = 33,
    val gravedad: Double = 3.71,
    val nivelRad: Int = 25,
    val aguaLiquida: Boolean = false,
    val toxicidadAtmos: Int = 15,
    val actTectonica: Int = 5,
    val tamano: Int = 25,
    val fechaDesc: LocalDate = LocalDate.of(1990, 1, 1),
    val distTierra: Double = 2.0,
    var aterrizado: Boolean = false
) {
    fun tempIdeal(): Boolean = temperatura in 0..40

    fun gravSoportable(): Boolean = gravedad in 3.0..15.0

    fun esHabitable(): Boolean = tempIdeal() && gravSoportable() && aguaLiquida && toxicidadAtmos < 30 && nivelRad < 40

    fun indicePeligrosidad() : Int = (nivelRad + toxicidadAtmos+ actTectonica ) /3

    fun esExplorable(): Boolean = indicePeligrosidad() < 60 && !esHabitable()
}