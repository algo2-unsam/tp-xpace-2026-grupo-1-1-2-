package ar.edu.unsam.algo2

import java.time.LocalDate
import java.time.Period

interface PerfilAptitud {
    fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean
}

interface Rol {
    fun CalcularBonus(tripulante: Tripulante): Double
}

class Tripulante(
    val nombre: String,
    val apellido: String,
    val fechaNac: LocalDate,
    val fechaInicio: LocalDate,
    var rol: Rol,
    var perfil: PerfilAptitud,
    val salarioBase: Double,
    val baseAsignada: BaseLanzamiento
) {
    var misExitosa: Int = 0
    var misFallidas: Int = 0
    var misParcial: Int = 0
    var añosExp: Int = 0
    var misionActual: Misiones? = null // Para saber si está ocupado
    val misionesCompletadas = mutableListOf<Misiones>()

    fun experiencia(): Int {
        añosExp = añosActivo() + (misExitosa / 2) + (misFallidas / 2) + (misParcial / 4)
        return añosExp
    }

    fun añosActivo(): Int {
        return Period.between(fechaInicio, LocalDate.now()).years
    }

    fun sumaMisionExitosa(): Boolean {
        misExitosa += 1
        return true
    }

    fun sumaMisionFallida(): Boolean {
        misFallidas += 1
        return true
    }

    fun sumaMisionParcial(): Boolean {
        misParcial += 1
        return true
    }

    fun condicionBase(): Boolean {
        return (experiencia() > 3) && (misionActual == null)
    }

    fun porcentajeSalario(porcentaje: Double): Double {
        return salarioBase * porcentaje
    }

    fun esApto(mision: Misiones): Boolean {//es apto si no está en otra misión, por ahora
        return misionActual == null && perfil.cumpleCondiciones(this, mision, mision.planetaAsig, mision.naveAsig)
    }

    fun esValido(): Boolean {
        return nombre.isNotBlank() &&
                apellido.isNotBlank() &&
                fechaNac.isBefore(LocalDate.now())
        // 'rol' y 'baseAsignada' son obligatorios por constructor (no pueden ser null)
    }
}

class Comandante(
    var porcentajeBase: Double = 0.5,
    var porcentajePlus: Double = 0.05
) : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        val base = tripulante.porcentajeSalario(porcentajeBase)
        val extra = tripulante.porcentajeSalario(porcentajePlus) * tripulante.misExitosa
        return base + extra
    }
}

class Piloto(var porcentaje: Double = 0.3) : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return tripulante.porcentajeSalario(porcentaje)
    }
}

class Ingeniero(
    var porcentajeCarguero: Double = 0.4,
    var porcentajeSinCarguero: Double = 0.2
) : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        val ultimaNave = tripulante.misionesCompletadas.lastOrNull()?.naveAsig
        val porcentaje = if (ultimaNave is Carguero) porcentajeCarguero else porcentajeSinCarguero
        return tripulante.porcentajeSalario(porcentaje)
    }
}

class Cientifico(var porcentaje: Double = 0.10) : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return tripulante.porcentajeSalario(porcentaje) * tripulante.misExitosa //mision exitosa y planeta aterrizado no es lo mismo
    }
}

class Medico(
    var porcentaje: Double = 0.25,
    var porcentajeAdicional: Double = 0.02
) : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return tripulante.porcentajeSalario(porcentaje) + (tripulante.porcentajeSalario(porcentajeAdicional) * tripulante.misFallidas)
    }
}

class Conformista : PerfilAptitud {
    override fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean {
        return tripulante.condicionBase()
    }
}

class Prudente : PerfilAptitud {
    override fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean {
        return (planeta.tempIdeal()) && (planeta.gravSoportable())
    }
}

class Explorador : PerfilAptitud {
    override fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean {
        return planeta.aterrizado == false
    }
}

class Veterano(val maximoDias: Int) : PerfilAptitud {
    override fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean {
        return mision.duracion() < maximoDias
    }
}

class Cauteloso(val maximoRadiacion: Int) : PerfilAptitud {

    override fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean {
        return planeta.nivelRad < maximoRadiacion
    }
}

class Exigente : PerfilAptitud {
    override fun cumpleCondiciones(tripulante: Tripulante, mision: Misiones, planeta: Planetas, nave: Naves): Boolean {
        return nave.esModerna()
    }
}

