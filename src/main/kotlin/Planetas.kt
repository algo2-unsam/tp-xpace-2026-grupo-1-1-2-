package ar.edu.unsam.algo2

import java.time.LocalDate

class Planetas(
    val nombre: String,
    val temperaturaMedia: Int,
    val gravedad : Int,
    val nivelRad: Int,
    val aguaLiquida: Boolean,
    val toxicidadAtmos: Int,
    val actTectonica: Int,
    val tamaño: Int,
    val fechaDesc: LocalDate,
    val distTierra: Int,
    var aterrizado: Boolean,
    val temperatura: Int
){
    fun tempIdeal(): Boolean{
        return temperatura > 0 && temperatura < 40
    }
    fun gravSoportable(): Boolean{
        return gravedad > 3 && gravedad < 15
    }
    fun cambiaAterrizaje(): Boolean {
        if (!aterrizado) {aterrizado = true}
        return aterrizado
    }
}


