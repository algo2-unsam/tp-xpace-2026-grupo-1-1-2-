package ar.edu.unsam.algo2
import java.awt.Point
import java.time.LocalDate
import java.time.Period
import kotlin.properties.Delegates

class Tripulante(
    val nombre: String = "",
    val apellido: String = "",
    var salarioBase: Double = 10.0,
    var rol: IRol = Comandante(),
    var perfil: IAptitud = Conformista(),
    val fechaNac: LocalDate = LocalDate.of(1980, 11, 23),
    val fechaInicio: LocalDate = LocalDate.of(2000, 1, 1),
    var baseAsignada: BaseLanzamiento,
    var misExitosas: Int = 2,
    var misFallidas: Int = 4,
    var misParcialmenteExitosas: Int = 8,
    var alcanceBase: Int,
    var ubicacion: Point = Point(2, 4)
){
    var distanciaLejana: Int = 10
    var misionActual: Mision? = null
    val misiones = mutableSetOf<Mision>()
    fun aniosActivo(): Int = Period.between(fechaInicio, LocalDate.now()).years
    fun experiencia(): Int = aniosActivo() + (misExitosas/2) + (misFallidas/2) + (misParcialmenteExitosas/4)
    fun salarioTotal(): Double = salarioBase + rol.bonusSalario(this)
    fun esApto(): Boolean {TODO()}

    fun completarMision() {misExitosas += 1}
    fun fracasarMision() {misFallidas += 1}
    fun cancelarMision() {misParcialmenteExitosas += 1}
    fun desasignarMision() {misionActual = null}

    fun baseEsCercana(): Boolean = ubicacion.distance(baseAsignada.coordenadasBase())  < distanciaLejana
}

interface IAptitud {
    fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean
}

class Conformista() : IAptitud{
    override fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean = true
}

class Prudente(mision: Mision) : IAptitud {
    override fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean = planeta.tempIdeal()
}

class Explorador() : IAptitud {
    override fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean = planeta.fueAterrizado
}

class Veterano(var maximoDiasSoportados: Int, mision: Mision) : IAptitud {
    override fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean = mision.duracionEstimada() < maximoDiasSoportados
}

class Cauteloso(var umbralRadiacionMax: Double): IAptitud {
    override fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean = planeta.radiacion < umbralRadiacionMax
}

class ExigenteConNave() : IAptitud {
    override fun esApto(mision: Mision, nave: Nave, planeta: Planeta): Boolean = nave.esModerna()
}

interface IRol {
    fun bonusSalario(tripulante: Tripulante): Double
}

class Comandante(): IRol {
    override fun bonusSalario(tripulante: Tripulante): Double = tripulante.salarioBase*0.5 + (porcentaje(tripulante)*tripulante.misExitosas)
    fun porcentaje(tripulante: Tripulante): Double = tripulante.salarioBase*0.05
}

class Piloto(): IRol {
    override fun bonusSalario(tripulante: Tripulante): Double = tripulante.salarioBase * 0.3
}

class Ingeniero(): IRol {
    override fun bonusSalario(tripulante: Tripulante): Double {
        TODO("Not yet implemented")
    }
}

class Cientifico(): IRol {
    override fun bonusSalario(tripulante: Tripulante): Double = cantidadAterrizajes(tripulante) * tripulante.salarioBase
    fun cantidadAterrizajes(tripulante: Tripulante): Int = tripulante.misiones.count { it -> it.planeta.fueAterrizado}
}

class Medico(): IRol {
    override fun bonusSalario(tripulante: Tripulante): Double = tripulante.salarioBase * bonusEstres(tripulante)
    fun bonusEstres(tripulante: Tripulante): Int = if(tripulante.misFallidas>0) return tripulante.misFallidas*2 else return 1
}



