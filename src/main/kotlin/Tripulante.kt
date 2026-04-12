package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period

interface Rol {
    fun calcularBonus(tripulante: Tripulante): Double
}
class Comandante : Rol {
    override fun calcularBonus(tripulante: Tripulante): Double = (tripulante.salarioBase * 0.50) +(tripulante.misExitosa *(tripulante.salarioBase * 0.05) )
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
    override fun calcularBonus(tripulante: Tripulante): Double = tripulante.salarioBase * (0.10 * planetasAterrizadosHistorial)
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
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean = mision.planeta.tempIdeal() && mision.planeta.gravSoportable()
}
class Explorador : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean = !mision.planeta.aterrizado
}
class Veterano(val maximoDiasSoportados: Int) : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean = mision.duracionEstimada() <= maximoDiasSoportados
}
class Cauteloso(val umbralRadiacionMax: Int) : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean = mision.planeta.nivelRad < umbralRadiacionMax
}
class ExigenteConNave : PerfilAptitud {
    override fun esApto(tripulante: Tripulante, mision: Mision): Boolean = mision.nave.esModerna()
}
class Tripulante(
    val nombre: String = "",
    val apellido: String = "",
    var salarioBase: Double = 10.0,
    var rol: Rol = Comandante(),
    var perfil: PerfilAptitud = Conformista(),
    val fechaNac: LocalDate = LocalDate.of(1980, 11, 23),
    val fechaInicio: LocalDate = LocalDate.of(2000, 1, 1),
    var baseAsignada: BaseLanzamiento = BaseLanzamiento(),
    var misExitosa: Int = 2,
    var misFallidas: Int = 4,
    var misParcial: Int = 8

) {
    var misionActual: Mision? = null
    var añosActivo = Period.between(fechaInicio, LocalDate.now()).years

    fun experiencia(): Int = añosActivo + (misExitosa / 2) + (misFallidas / 2) + (misParcial / 4)

    fun esAptoPara(mision: Mision): Boolean = (experiencia() >= 3) && (misionActual == null) && (perfil.esApto(this, mision))

    fun salarioTotal(): Double = salarioBase + rol.calcularBonus(this)

    fun baseCercana(otraBase: BaseLanzamiento, kmMaximos: Double): Boolean = baseAsignada.direccion.ubiGeo.distance(otraBase.direccion.ubiGeo) <= kmMaximos
}