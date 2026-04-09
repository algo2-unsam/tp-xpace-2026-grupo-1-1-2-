package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates

abstract class Nave(
        open var nombre: String = "Atenea",
        open val id: String = "AD741",
        open val fechaFabricacion: LocalDate = LocalDate.of(1990, 1, 1),
        open var velocidadPromedio: Double = 100.0,
        open val autonomia: Double = 500.0,
        open val consumoBase: Double = 5.1
        ){
    var enMision: Boolean = false
    fun antiguedad() : Long = ChronoUnit.YEARS.between(fechaFabricacion, LocalDate.now())
    fun esModerna() : Boolean = antiguedad() < 5
    fun puedeAlcanzar(distancia : Double) : Boolean = distancia*365/velocidadPromedio*2 <= autonomia
    fun consumoTotal(distanciaPlaneta: Double): Double = consumoTotal()*distanciaPlaneta
    open fun consumoTotal(): Double {return consumoBase}
    /*
    open fun consumo_anios_luz() : Double {return consumo}
    open fun es_apta() : Boolean = true
    */
}

abstract class NaveTransportadora(
    override var nombre: String,
    override val id : String,
    override val fechaFabricacion: LocalDate,
    override var velocidadPromedio: Double,
    override val autonomia: Double,
    override val consumoBase: Double,
    var capacidadMaxima: Int
    ) : Nave(nombre, id, fechaFabricacion, velocidadPromedio, autonomia, consumoBase) {
    fun añadir
}

class Sonda(
    override var nombre:String = "Sonda",
    override val id: String = "S-001",
    override val fechaFabricacion: LocalDate = LocalDate.of(2000, 1, 1),
    override var velocidadPromedio:Double = 100.0,
    override val autonomia:Double = 500.0,
    override val consumoBase:Double = 10.0
) : Nave(nombre, id, fechaFabricacion, velocidadPromedio, autonomia, consumoBase) {

}

class Transbordador (
    override var nombre:String = "Transbordador",
    override val id: String = "S-002",
    override val fechaFabricacion: LocalDate = LocalDate.of(2000, 1, 1),
    override var velocidadPromedio:Double = 100.0,
    override val autonomia:Double = 500.0,
    override val consumoBase:Double = 10.0,
    val capacidadMaxima: Int = 5
) : Nave(nombre, id, fechaFabricacion, velocidadPromedio, autonomia, consumoBase) {
    var tripulantes = mutableSetOf<Tripulante>()

    fun cantidadTripulantes(): Int = tripulantes.size
    fun tieneCapacidad(): Boolean = capacidadMaxima > cantidadTripulantes()
    fun añadirTripulante(tripulante:Tripulante) {if (tieneCapacidad()) tripulantes.add(tripulante)}
    override fun consumoTotal(): Double = super.consumoTotal() + (super.consumoTotal() *cantidadTripulantes())
    fun liberarNave() {tripulantes.clear()}
}

class Carguero (
    override var nombre:String = "Carguero",
    override val id: String = "S-003",
    override val fechaFabricacion: LocalDate = LocalDate.of(1990, 1, 1),
    override var velocidadPromedio:Double = 100.0,
    override val autonomia:Double = 500.0,
    override val consumoBase:Double = 10.0,
    val capacidad : Double = 20.0,
) : Nave(nombre, id, fechaFabricacion, velocidadPromedio, autonomia, consumoBase) {
    var carga: Double = 0.0
    fun tieneCapacidad(): Boolean = carga < capacidad
    fun añadirCarga(kg: Double) {if(tieneCapacidad()) carga += kg}
    fun bonusAntiguedad(): Double {if( antiguedad()>10 ) return 1.2 else return 1.1}
    override fun consumoTotal(): Double = (super.consumoTotal() + (0.1*carga))*bonusAntiguedad()

    fun liberarNave() {carga = 0.0}
}

/*
 Realizar metodo es_apto en Transbordador (requiere Base Lanzamiento)
 Realizar implementacion de tripulantes en Sonda
 Ver tema interfaz
*/
