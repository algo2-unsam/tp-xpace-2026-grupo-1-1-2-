package ar.edu.unsam.algo2
import java.awt.Point
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
    open val toxicidadAtmos: Double = 0.0
    open val actTectonica: Double = 0.0
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

    fun pocaToxicidad(): Boolean {
        return toxicidadAtmos < 30
    }

    fun pocaRad(): Boolean {
        return nivelRad <40
    }

    fun tieneAguaLiquida(): Boolean {
        return aguaLiquida
    }

    fun esHabitable(): Boolean {
        return pocaRad() && pocaToxicidad() && gravSoportable() && tempIdeal() && tieneAguaLiquida()
    }

    fun indicePeligrosidad() : Double {
        return (nivelRad + toxicidadAtmos+ actTectonica ) /3
    }

    fun esExplorable(): Boolean {
        return (indicePeligrosidad() < 60 && ! esHabitable())
    }

}
class Pluton : Planetas() {
    override val nombre: String = "Pluton"
    override val temperaturaMedia: Int = 5
    override val gravedad: Int = 6
    override val nivelRad: Int = 10
    override val aguaLiquida: Boolean = true
    override val toxicidadAtmos: Double = 5.0
    override val actTectonica: Double = 5.0
    override val tamano: Int = 10
    override val fechaDesc: LocalDate = LocalDate.of(2020, 1, 1)
    override val distTierra: Int = 5
    override var aterrizado: Boolean = true
    override val temperatura: Int = 5
}

abstract class Naves(
    val nombre: String,
    val codigo: String,
    val fechaFabrica: Int,
    val velocidad: Int,
    val autonomia: Int,
    val consumoNave: Double,
){

    abstract fun consumoEspecifico(): Double
    val antiguedad = 2026 - fechaFabrica
    fun consumoCombu(distanciaPlaneta : Int): Double{
        return consumoEspecifico() * distanciaPlaneta
    }

    //fun enMision(): Boolean { ]//ver essto con los chicos

    fun puedeAlcanzar (distanciaPlaneta : Int): Boolean {
        return ((distanciaPlaneta *365)/velocidad*2) <= autonomia
    }

}

class Sonda(
    nombre: String,
    codigo: String,
    fechaFabrica: Int,
    velocidad: Int,
    autonomia: Int,
    consumoNave: Double
) : Naves(nombre, codigo, fechaFabrica, velocidad, autonomia, consumoNave) {
    override fun consumoEspecifico(): Double {
        return consumoNave
    }
}

class Transbordador(
    nombre: String,
    codigo: String,
    fechaFabrica: Int,
    velocidad: Int,
    autonomia: Int,
    consumoNave: Double,
    val numPasajeros: Int,
) : Naves(nombre, codigo, fechaFabrica, velocidad, autonomia, consumoNave){
    override fun consumoEspecifico(): Double {
        return consumoNave + ((consumoNave *0.1) * numPasajeros)
    }
}

class Carguero(
    nombre: String,
    codigo: String,
    fechaFabrica: Int,
    velocidad: Int,
    autonomia: Int,
    consumoNave: Double,
    val capacidadCarga: Double
) : Naves(nombre, codigo, fechaFabrica, velocidad, autonomia, consumoNave){
    override fun consumoEspecifico(): Double {
        return if (antiguedad < 10){
            consumoNave + (consumoNave * 0.05 * capacidadCarga)
        } else{
            (consumoNave + (consumoNave * 0.05 * capacidadCarga)) *1.2
        }
    }
}

abstract class Misiones

class Mision1 : Misiones()

class BaseLanzamiento(
    val nombre: String,
    val maximoNaves: Int,
    val ubicacion : Direccion
){
    val NavesEstacionadas = listOf<Naves>()

}

class Direccion(
    val pais : String,
    val Ciudad : String,
    val calle : String,
    val altura : Int,
    val ubiGeo : Point
){}
