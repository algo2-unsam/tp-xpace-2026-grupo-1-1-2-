package ar.edu.unsam.algo2

abstract class Tripulante { // De esta clase heredan los roles
    // var perfil_aptitud : Perfil_aptitud = COMPOSICION
    fun salario() : Double {TODO("Not yet implemented")}
    abstract fun bonus_salario() : Double
}

class Comandante : Tripulante() {
    override fun bonus_salario(): Double { TODO("Not yet implemented") }
}

class Piloto() : Tripulante() {
    override fun bonus_salario(): Double { TODO("Not yet implemented") }
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
