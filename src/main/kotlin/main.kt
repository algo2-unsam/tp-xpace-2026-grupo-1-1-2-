package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period

abstract class Tripulante {
    open val nombre: String = ""
    open val apellido: String = ""
    open val fechaNac: LocalDate = LocalDate.now()
    open var edad: Int = 0
    open var misExitosa: Int = 0
    open var misFallidas: Int = 0
    open var misParcial: Int = 0
    open val fechaInicio: LocalDate = LocalDate.now()
    open var aniosExp: Int = 0

    fun aniosActivo(): Int {
        return Period.between(fechaInicio, LocalDate.now()).years
    }
    fun experiencia(): Int {
        aniosExp = aniosActivo() + (misExitosa / 2) + (misFallidas / 2) + (misParcial / 4)
        return aniosExp
    }
    fun calculaEdad(): Int {
        edad = Period.between(fechaNac, LocalDate.now()).years
        return edad
    }
}
class Tripulante1 : Tripulante() {
    override val nombre = "Juan"
    override val apellido = "Perez"
    override val fechaNac = LocalDate.of(2004, 8, 5)
    override var edad = 0
    override var misExitosa = 10
    override var misFallidas = 6
    override var misParcial = 12
    override val fechaInicio = LocalDate.of(2000, 1, 1)
    override var aniosExp = 0
}

abstract class Planetas {
    open val nombre: String = ""
    open val temperaturaMedia: Int = 0
    open val gravedad: Int = 0
    open val nivelRad: Int = 0
    open val aguaLiquida: Boolean = true
    open val toxicidadAtmos: Int = 0
    open val actTectonica: Int = 0
    open val tamano: Int = 0
    open val fechaDesc: LocalDate = LocalDate.now()
    open val distTierra: Int = 0
    open var aterrizado: Boolean = true
    open val temperatura: Int = 0

    fun tempIdeal(): Boolean {
        return temperaturaMedia in 0..40
    }
    fun gravSoportable(): Boolean {
        return gravedad in 3..15
    }
}
class Pluton : Planetas() {
    override val nombre: String = "Pluton"
    override val temperaturaMedia: Int = 5
    override val gravedad: Int = 6
    override val nivelRad: Int = 10
    override val aguaLiquida: Boolean = true
    override val toxicidadAtmos: Int = 5
    override val actTectonica: Int = 5
    override val tamano: Int = 10
    override val fechaDesc: LocalDate = LocalDate.of(2020, 1, 1)
    override val distTierra: Int = 5
    override var aterrizado: Boolean = true
    override val temperatura: Int = 5
}

abstract class Naves

class Sonda : Naves()

class Transbordador : Naves()

class Carguero : Naves()

abstract class Misiones

class Mision1 : Misiones()
