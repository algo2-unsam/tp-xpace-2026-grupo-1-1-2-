package ar.edu.unsam.algo2
import java.time.LocalDate

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
    fun puede_alcanzar() : Boolean = planeta.distancia_tierra*365 / nave.velocidad_promedio*2 <= nave.autonomia
    fun tripulantes_son_aptos() : Boolean = tripulantes.any(predicate={it -> !it.es_apto()})
    fun puede_lanzar() : Boolean = puede_alcanzar() // Quizas sea responsabilidad de Nave
    fun alto_riego() : Boolean = !planeta.es_habitable() && duracion_estimada() > 500
}


/*
    Implementar estados de Mision
    Implementar metodo puede_lanzar()
 */