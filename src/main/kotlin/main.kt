package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period
import org.uqbar.geodds.Point
import java.math.BigDecimal

/*
fun main() {
    println("Hola mundo")

    val planeta = Planeta("Marte", 20, 9.8, 10, true, 10, 5, 0.5)
    println(planeta.esHabitable())
} //sino no me corria??????
*/


interface Rol {
    fun calcularBonus(tripulante: Tripulante): Double
}

class Comandante : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double =
        (tripulante.salarioBase * 0.50) +(tripulante.misExitosa *(tripulante.salarioBase * 0.05) )
}

class Piloto : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double =
        tripulante.salarioBase * 0.30
}

class Ingeniero(val ultimaMisionFueCarguero: Boolean) : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double {
        val porcentaje = if (ultimaMisionFueCarguero) 0.40 else 0.20
        return tripulante.salarioBase * porcentaje
    }
}

class Cientifico(val planetasAterrizadosHistorial: Int) : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double =
        tripulante.salarioBase * (0.10 * planetasAterrizadosHistorial)
}

class Medico : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double {
        val bonusBase = tripulante.salarioBase * 0.25
        val extraPorEstres = tripulante.misFallidas * (tripulante.salarioBase * 0.02)
        return bonusBase + extraPorEstres
    }
}

interface PerfilAptitud {
    fun esApto(tripulante: Tripulante, mision: Mision): Boolean
}

class Conformista : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision) = true
}

class Prudente : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean {
        return mision.planeta.tempIdeal() && mision.planeta.gravSoportable()
    }
}

class Explorador : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean {
        return !mision.planeta.aterrizado
    }
}

class Veterano(val maximoDiasSoportados: Int) : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean {
        return mision.duracionEstimada() <= maximoDiasSoportados
    }
}

class Cauteloso(val umbralRadiacionMax: Int) : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean {
        return mision.planeta.nivelRad < umbralRadiacionMax
    }
}

class ExigenteConNave : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean {
        return mision.nave.esModerna()
    }
}

class Tripulante(
    val nombre: String = "",
    val apellido: String = "",
    var salarioBase: Double = 10.0,
    var rol: Rol = Comandante(),
    var perfil: PerfilAptitud = Conformista()
) {
    val fechaNac: LocalDate = LocalDate.of(1980, 11, 23)
    val fechaInicio: LocalDate = LocalDate.of(2000, 1, 1)
    var baseAsignada: BaseLanzamiento = BaseLanus()
    var misExitosa: Int = 1
    var misFallidas: Int = 0
    var misParcial: Int = 0
    var misionActual: Mision? = null
    var añosActivo = Period.between(fechaInicio, LocalDate.now()).years

    fun experiencia(): Int = añosActivo + (misExitosa / 2) + (misFallidas / 2) + (misParcial / 4)

    fun esAptoPara(mision: Mision): Boolean =
        experiencia() >= 3 && misionActual == null && perfil.esApto(this, mision)

    fun salarioTotal(): Double = salarioBase + rol.calcularBonus(this)

    fun baseCercana(otraBase: BaseLanzamiento, kmMaximos: Double): Boolean {
        return baseAsignada.direccion.ubiGeo.distance(otraBase.direccion.ubiGeo) <= kmMaximos
    }
}

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
class Pluton: Planeta() {
    override val nombre = "Pluton"
}

abstract class Nave(
    val nombre: String = "Atenea",
    val codigo: String = "AD741",
    val fechaFab: LocalDate = LocalDate.of(1990, 1, 1),
    val velocidadProm: Double = 100.0,
    val autonomia: Double = 500.0,
    var consumoBase: Double = 5.1
) {
    var enMision: Boolean = false


    val antiguedad: Int get() = Period.between(fechaFab, LocalDate.now()).years

    fun esModerna(): Boolean = antiguedad < 5

    fun puedeAlcanzar(planeta: Planeta): Boolean =
        (planeta.distTierra * 365 / velocidadProm * 2) <= autonomia

    // uso  'unidades' para evitar el warning de nombres distintos entre tripulantes y carga
    abstract fun cuantoConsume(planeta: Planeta, unidades: Int): Double

    fun consumoTotal(planeta: Planeta, unidades: Int): Double {
        return cuantoConsume(planeta, unidades) * planeta.distTierra
    }

    fun esValida(): Boolean =
        nombre.isNotBlank() &&
                codigo.isNotBlank() &&
                velocidadProm > 0 &&
                autonomia > 0 &&
                consumoBase > 0

}

class Sonda(
    nombre: String = "Sonda",
    codigo: String = "S-001",
    fechaFab: LocalDate = LocalDate.of(2000, 1, 1),
    vel: Double = 100.0,
    aut: Double = 500.0,
    cons: Double = 10.0
) : Nave(nombre, codigo, fechaFab, vel, aut, cons) {
    override fun cuantoConsume(planeta: Planeta, unidades: Int): Double = consumoBase
}

class Transbordador(
    nombre: String = "Transbordador",
    codigo: String = "S-002",
    fechaFab: LocalDate = LocalDate.of(1990, 1, 1),
    vel: Double = 100.0,
    aut: Double = 500.0,
    cons: Double = 10.0,
    val capacidadMax: Int = 5
) : Nave(nombre, codigo, fechaFab, vel, aut, cons) {

    override fun cuantoConsume(planeta: Planeta, unidades: Int): Double {
        return consumoBase + (consumoBase * 0.10 * unidades)
    }
}

class Carguero(
    nombre: String = "Carguero",
    codigo: String = "S-003",
    fechaFab: LocalDate = LocalDate.of(1990, 1, 1),
    vel: Double = 100.0,
    aut: Double = 500.0,
    cons: Double = 10.0
) : Nave(nombre, codigo, fechaFab, vel, aut, cons) {

    override fun cuantoConsume(planeta: Planeta, unidades: Int): Double {
        val consumoConCarga = consumoBase + (consumoBase * 0.05 * unidades)
        return if (antiguedad > 10) consumoConCarga * 1.20 else consumoConCarga
    }
}

enum class EstadoMision { BORRADOR, EN_CURSO, COMPLETADA, FALLIDA, CANCELADA }

class Mision(
    val nombre: String="Mision1",
    val descripcion: String="Mision1",
    val fechaLanz: LocalDate = LocalDate.of(2027, 1, 1)
) {
    var tripulantes: MutableList<Tripulante> = mutableListOf()
    var nave: Nave= Transbordador()
    var planeta: Planeta= Pluton()
    var estado: EstadoMision = EstadoMision.BORRADOR

    fun lanzar(): Boolean {
        val mismaBaseYNaveEnBase = if (tripulantes.isNotEmpty()) {
            val baseComun = tripulantes.first().baseAsignada
            tripulantes.all { it.baseAsignada == baseComun } && baseComun.navesEstacionadas.contains(nave)
        } else true

        val condiciones = estado == EstadoMision.BORRADOR &&
                nave.puedeAlcanzar(planeta) &&
                tripulantes.all { it.esAptoPara(this) } &&
                capacidadValida() &&
                mismaBaseYNaveEnBase

        if (condiciones) {
            estado = EstadoMision.EN_CURSO
            nave.enMision = true
            tripulantes.forEach { it.misionActual = this }
            return true
        }
        return false
    }
    fun completar(): Boolean {
        if (estado == EstadoMision.EN_CURSO) {
            estado = EstadoMision.COMPLETADA
            planeta.aterrizado = true
            tripulantes.forEach {
                it.misExitosa += 1
                it.misionActual = null // Liberamos al tripulante
            }
            nave.enMision = false
            return true
        }
        return false
    }

    fun fallar(): Boolean {
        estado = EstadoMision.FALLIDA
        tripulantes.forEach {it.misFallidas+=1}
        nave.enMision = false
        return true
    }
    fun cancelar(): Boolean {
        if (estado == EstadoMision.EN_CURSO && altoRiesgo()){
            estado = EstadoMision.CANCELADA
            nave.enMision = false
            tripulantes.forEach {it.misParcial+=1}
            return true
        }
        return false
    }

    fun capacidadValida(): Boolean {
        return if (nave is Transbordador) {
            tripulantes.size <= (nave as Transbordador).capacidadMax
        } else true
    }

    fun duracionEstimada(): Double = (planeta.distTierra * 365 / nave.velocidadProm) * 2

    fun altoRiesgo(): Boolean = !planeta.esHabitable() && duracionEstimada() > 500
}

abstract class BaseLanzamiento() {
    open val nombre: String = ""
    val direccion: Direccion = Direccion()
    open val capacidadMax: Int = 10

    val navesEstacionadas = mutableListOf<Nave>()
}

class BaseLanus: BaseLanzamiento() {
    override val nombre = "Base1"
}

class Direccion(){
    open val pais : String = ""
    open val ciudad : String = ""
    open val calle : String = ""
    open val altura : Int = 0
    open val ubiGeo : Point = Point(0, 0)

}
