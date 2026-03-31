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
    val salarioBase: Double
) {
    var misExitosa: Int = 0
    var misFallidas: Int = 0
    var misParcial: Int = 0
    var añosExp: Int = 0
    var misionActual: Misiones? = null // Para saber si está ocupado
    val misionesCompletadas = mutableListOf<Misiones>()

    fun experiencia():Int{
        añosExp = añosActivo() + (misExitosa/2) + (misFallidas/2) + (misParcial/4)
        return añosExp
    }
    fun añosActivo(): Int {
        return Period.between(fechaInicio, LocalDate.now()).years
    }
    fun sumaMisionExitosa(): Boolean{
        misExitosa += 1
        return true
    }
    fun sumaMisionFallida(): Boolean{
        misFallidas += 1
        return true
    }
    fun sumaMisionParcial(): Boolean{
        misParcial += 1
        return true
    }

    fun condicionBase(): Boolean{
        return (experiencia() > 3) && (misionActual == null)
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

class Comandante : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return (tripulante.salarioBase * 0.5) + (tripulante.salarioBase * 0.05 * tripulante.misExitosa)
    }
}

class Piloto : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return tripulante.salarioBase * 0.3
    }
}

class Ingeniero : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        val ultimaNave = tripulante.misionesCompletadas.lastOrNull()?.naveAsig
        val porcentaje = if (ultimaNave is Carguero) 0.4 else 0.2
        return tripulante.salarioBase * porcentaje
    }
}

class Cientifico : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return tripulante.salarioBase * 0.10 * tripulante.misExitosa
    }
}

class Medico : Rol {
    override fun CalcularBonus(tripulante: Tripulante): Double {
        return (tripulante.salarioBase * 0.25) + (tripulante.salarioBase * 0.02 * tripulante.misFallidas)
    }
}


