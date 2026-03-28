package ar.edu.unsam.algo2

class Mision() {

}

abstract class Tripulante (var salario_base: Double){ // De esta clase heredan los roles
    // var perfil_aptitud : Perfil_aptitud = COMPOSICION
    fun salario() : Double {return salario_base + bonus_salario()}
    abstract fun bonus_salario() : Double
    fun experiencia():Double {return TODO()}
    var mision_asignada : Mision

    fun es_apto():Boolean {return experiencia()>3 && !mision_asignada.en_curso() && condiciones_aptitud()}
}

class Comandante(salario_base: Double) : Tripulante(salario_base) {
    override fun bonus_salario(): Double {super.salario_base + (super.salario_base*0.05)*super.misiones_exitosas()}
}

class Piloto(salario_base: Double) : Tripulante(salario_base) {
    override fun bonus_salario(): Double {super.salario_base * 0.3}
}

class Ingeniero() : Tripulante() {
    override fun bonus_salario(): Double { TODO("Not yet implemented") }
}

class Cientifico() : Tripulante() {
    override fun bonus_salario(): Double { TODO("Not yet implemented") }
}

class Medico() : Tripulante() {
    override fun bonus_salario(): Double { TODO("Not yet implemented") }
}

abstract class Perfil_aptitud() { // O podria ser una interfaz, no lo se
    open fun cumple_conidicion_perfil() : Boolean {TODO("Not yet implemented")}
}

open class Aptitud() {
    abstract class cumple_condiciones()
}

class Conformista() : Aptitud() {
    fun cumple_condiciones() : Boolean {return true}
}

class Prudente : Aptitud() {
    fun cumple_condiciones(): Boolean {return planeta_asignado.temperatura_ideal() && planeta_asignado.gravedad_soportable()}
}

class Explorador() : Aptitud() {
    fun cumple_condiciones(): Boolean {return planeta_asignado.fue_aterrizado()}
}

class Veterano() : Aptitud() {
    fun cumple_condiciones(): Boolean {mision_asignada.duracion_estimada() <= maximo_dias}
}

class Cauteloso() : Aptitud() {
    fun cumple_condiciones() : Boolean {planeta_asignado.radiacion() < umbral}
}

class Exigente() : Aptitud() {
    fun cumple_condiciones() : Boolean {nave_asignada.es_moderna()}
}