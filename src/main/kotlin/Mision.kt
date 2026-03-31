package ar.edu.unsam.algo2
import java.time.LocalDate

class Mision(
    var nombre:String,
    var descripcion:String,
    var fecha_lanzamiento: LocalDate,
    var en_curso : Boolean = false
    //tripulantes
    ){
    lateinit var planeta:Planeta
    lateinit var nave:Nave

    fun duracion_estimada():Double{return planeta.distancia_tierra*365/nave.velocidad_promedio*2}
}