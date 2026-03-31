package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period
import kotlin.properties.Delegates

/*
class Mision() {
    var en_curso: Boolean = false
    lateinit var planeta_asignado : Planeta
    fun tiene_temp_ideal():Boolean {TODO()}
    fun tiene_grav_soportable():Boolean {TODO()}
    var duracion_estimada by Delegates.notNull<Int>()
}
*/

class BaseLanzamiento() {
}

// --------------------------------------------------------------------------------------

data class DatosTripulante(
    var nombre: String,
    var apellido: String,
    val fecha_nacimiento: LocalDate,
    var misiones_exitosas: Int,
    var misiones_parcialmente_exitosas: Int,
    var misiones_fracasadas: Int,
    var fecha_inicio_actividad: LocalDate,
    var salario_base: Double
){}

abstract class Tripulante(open var data : DatosTripulante) {
    var nombre = data.nombre
    var apellido = data.apellido
    val fecha_nacimiento = data.fecha_nacimiento
    val misiones_exitosas = data.misiones_exitosas
    var misiones_parcialmente_exitosas = data.misiones_parcialmente_exitosas
    var misiones_fracasadas = data.misiones_fracasadas
    var fecha_inicio_actividad = data.fecha_inicio_actividad
    var salario_base = data.salario_base

    lateinit var mision : Mision
    lateinit var aptitud : Aptitud
    lateinit var nave : Nave

    val misiones_asignadas = mutableSetOf(mision)

    fun anios_actividad(): Int = Period.between(fecha_inicio_actividad, LocalDate.now()).years
    fun experiencia():Int = anios_actividad()+(misiones_exitosas/2)+(misiones_fracasadas/2)+(misiones_parcialmente_exitosas/4)
    fun salario():Double = salario_base + bonus_salario()
    abstract fun bonus_salario():Double
    fun es_apto(): Boolean = cumple_condiciones_base() && aptitud.cumple_condiciones(mision, mision.planeta, nave)
    fun cumple_condiciones_base(): Boolean = experiencia() >= 3 && !mision_en_curso()
    fun mision_en_curso() : Boolean = misiones_asignadas.any({it -> it.en_curso})
}

class Comandante(override var data:DatosTripulante) : Tripulante(data) {
    override fun bonus_salario(): Double = salario_base*0.5 + (salario_base*0.05)*misiones_exitosas
}

class Piloto(override var data : DatosTripulante) : Tripulante(data){
    override fun bonus_salario(): Double = salario_base*0.3
}

class Ingeniero(override var data : DatosTripulante) : Tripulante(data){
    override fun bonus_salario(): Double {TODO()}
}

class Cientifico(override var data : DatosTripulante) : Tripulante(data){
    override fun bonus_salario(): Double {TODO()}
}

class Medico(override var data : DatosTripulante) : Tripulante(data){
    override fun bonus_salario(): Double {TODO()}
}

interface Aptitud {
    fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave): Boolean
}

class Conformista() : Aptitud {
    override fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave) : Boolean = true
}

class Prudente() : Aptitud {
    override fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave): Boolean = planeta.temp_ideal()
}

class Explorador() : Aptitud {
    override fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave): Boolean = planeta.fue_aterrizado
}

class Veterano(var maximo_dias:Int) : Aptitud {
    override fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave): Boolean = mision.duracion_estimada < maximo_dias
}

class Cauteloso(var umbral:Double) : Aptitud {
    override fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave) : Boolean = planeta.radiacion < umbral
}

class Exigente() : Aptitud {
    override fun cumple_condiciones(mision:Mision, planeta:Planeta, nave:Nave) : Boolean = nave.es_moderna()
}