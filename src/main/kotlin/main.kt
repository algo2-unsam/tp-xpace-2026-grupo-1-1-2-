package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period
import org.uqbar.geodds.Point
import java.math.BigDecimal

// geo soporte
data class Point(val x: BigDecimal, val y: BigDecimal) {
    fun distance(anotherPoint: Point): Double =
        Math.sqrt(Math.pow(anotherPoint.x.toDouble() - x.toDouble(), 2.0) +
                Math.pow(anotherPoint.y.toDouble() - y.toDouble(), 2.0))
}

// roles y aptitudes

interface Rol {
    fun calcularBonus(tripulante: Tripulante): Double
}

class Comandante : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double =
        (tripulante.salarioBase * 0.50) +(tripulante.misExitosa *(tripulante.salarioBase * 0.05) )//optimizar tripulante.salarioBase, hay una mejor manera. metodo
}

class Piloto : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double = tripulante.salarioBase * 0.30
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

// clase tripulante

/*
   abstract class Tripulante { ... }
   class Tripulante1 : Tripulante() { ... }

   tripulante recibe sus datos por constructor.
*/

class Tripulante(

    val nombre: String,
    val apellido: String,
    val fechaNac: LocalDate,
    val fechaInicio: LocalDate,
    var salarioBase: Double,
    var rol: Rol,
    var perfil: PerfilAptitud,
    var baseAsignada: BaseLanzamiento
) {
    /*init {
        require(nombre.isNotBlank()) { "nombre vacío" }
        require(apellido.isNotBlank()) { "apellido vacío" }
        require(fechaNac.isBefore(LocalDate.now())) { "fecha de nacimiento erronea" }
    }eliminar*/
    var misExitosa: Int = 0
    var misFallidas: Int = 0
    var misParcial: Int = 0
    var misionActual: Mision? = null //

    fun experiencia(): Int {
        val añosActivo = Period.between(fechaInicio, LocalDate.now()).years
        return añosActivo + (misExitosa / 2) + (misFallidas / 2) + (misParcial / 4)
    }

    // validación de aptitud
    fun esAptoPara(mision: Mision): Boolean =
        experiencia() >= 3 && misionActual == null && perfil.esApto(this, mision)

    fun salarioTotal(): Double = salarioBase + rol.calcularBonus(this)


    fun consideraCercana(otraBase: BaseLanzamiento, kmMaximos: Double): Boolean {
        return this.baseAsignada.ubicacion.distance(otraBase.ubicacion) <= kmMaximos
    }
}

// pLANETAS

/*
   abstract class Planetas { ... }
   class Pluton : Planetas() { ... }

*/

class Planeta(
    val nombre: String,
    val temperatura: Int,
    val gravedad: Double,
    val nivelRad: Int,
    val aguaLiquida: Boolean,
    val toxicidadAtmos: Int,
    val actTectonica: Int,
    val distTierra: Double // En años luz
) {
    var aterrizado: Boolean = false

    fun tempIdeal(): Boolean = temperatura in 0..40
    fun gravSoportable(): Boolean = gravedad in 3.0..15.0
    fun esHabitable(): Boolean =
        tempIdeal() && gravSoportable() && aguaLiquida && toxicidadAtmos < 30 && nivelRad < 40
}

// Naves

abstract class Nave(
    val nombre: String,
    val codigo: String,
    val fechaFab: LocalDate,
    val velocidadProm: Double,
    val autonomia: Double,
    val consumoBase: Double
) {
    var enMision: Boolean = false

    fun antiguedad(): Int = Period.between(fechaFab, LocalDate.now()).years
    fun esModerna(): Boolean = antiguedad() < 5

    // es *2
    fun puedeAlcanzar(planeta: Planeta): Boolean =
        (planeta.distTierra * 365 / velocidadProm * 2) <= autonomia

    abstract fun consumoTotal(planeta: Planeta, tripulantes: Int, carga: Double): Double // ver como delegar a clases concretas
}

class Transbordador(
    nombre: String, codigo: String, fechaFab: LocalDate, vel: Double, aut: Double, cons: Double,
    val capacidadMax: Int
) : Nave(nombre, codigo, fechaFab, vel, aut, cons) {

    /* val tripulantes = 6 */

    override fun consumoTotal(planeta: Planeta, tripulantes: Int, carga: Double): Double {
        val consumoPorViaje = consumoBase + (consumoBase * 0.10 * tripulantes)
        return consumoPorViaje * planeta.distTierra
    }
}

// mision es

/*
   open var estado: Int = 0 */

enum class EstadoMision { BORRADOR, EN_CURSO, COMPLETADA, FALLIDA, CANCELADA }

class Mision(
    val nombre: String,
    val planeta: Planeta,
    var nave: Nave,
    val tripulantes: MutableList<Tripulante> = mutableListOf()
) {
    var estado: EstadoMision = EstadoMision.BORRADOR

    fun lanzar() {
        val mismaBaseYNaveEnBase = if (tripulantes.isNotEmpty()) {
            val baseComun = tripulantes.first().baseAsignada
            tripulantes.all { it.baseAsignada == baseComun } && baseComun.navesEstacionadas.contains(nave)
        } else true

        val condiciones = estado == EstadoMision.BORRADOR &&
                nave.puedeAlcanzar(planeta) &&
                tripulantes.all { it.esAptoPara(this) } &&
                verificarCapacidadNave() &&
                mismaBaseYNaveEnBase // <--- Nuevo chequeo

        if (condiciones) {
            estado = EstadoMision.EN_CURSO
            nave.enMision = true
            tripulantes.forEach { it.misionActual = this }
        }
    }

    private fun verificarCapacidadNave(): Boolean {
        return if (nave is Transbordador) tripulantes.size <= (nave as Transbordador).capacidadMax else true
    }
    fun duracionEstimada(): Double {
        // dis * 365 / vel* 2
        return (planeta.distTierra * 365 / nave.velocidadProm) * 2
    }
}

class BaseLanzamiento(
    val nombre: String,
    val ubicacion: Point,
    val capacidadMax: Int
) {
    val navesEstacionadas = mutableListOf<Nave>()
}