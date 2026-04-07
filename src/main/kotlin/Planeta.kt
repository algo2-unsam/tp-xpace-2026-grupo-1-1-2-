package ar.edu.unsam.algo2

import java.time.LocalDate

abstract class Planeta(
    open val nombre: String = "",
    open val temperatura: Int = 63,
    open val gravedad: Double = 3.71,
    open val nivelRad: Int = 25,
    open val aguaLiquida: Boolean = false,
    open val toxicidadAtmos: Int = 95,
    open val actTectonica: Int = 0,
    open val tamano: Int = 25,
    open val fechaDesc: LocalDate = LocalDate.of(1990, 1, 1),
    open val distTierra: Double = 2.0// En años luz
) {
    var aterrizado: Boolean = false

    fun tempIdeal(): Boolean = temperatura in 0..40

    fun gravSoportable(): Boolean = gravedad in 3.0..15.0

    fun esHabitable(): Boolean =
        tempIdeal() && gravSoportable() && aguaLiquida && toxicidadAtmos < 30 && nivelRad < 40

    fun indicePeligrosidad() : Int = (nivelRad + toxicidadAtmos+ actTectonica ) /3

    fun esExplorable(): Boolean {
        return (indicePeligrosidad() < 60 && !esHabitable())
    }
}