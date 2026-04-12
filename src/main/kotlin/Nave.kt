package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period

abstract class Nave(
    val nombre: String = "Atenea",
    val codigo: String = "AD741",
    val fechaFab: LocalDate = LocalDate.of(1990, 1, 1),
    val velocidadProm: Double = 100.0,
    val autonomia: Double = 500.0,
    var consumoBase: Double = 5.1,
    var enMision: Boolean = false
) {
    val antiguedad: Int get() = Period.between(fechaFab, LocalDate.now()).years

    fun esModerna(): Boolean = antiguedad < 5

    fun puedeAlcanzar(planeta: Planeta): Boolean = (planeta.distTierra * 365 / velocidadProm * 2) <= autonomia

    abstract fun cuantoConsume(planeta: Planeta, unidades: Int): Double

    fun consumoTotal(planeta: Planeta, unidades: Int): Double = cuantoConsume(planeta, unidades) * planeta.distTierra

    fun esValida(): Boolean =
        nombre.isNotBlank() && codigo.isNotBlank() && velocidadProm > 0 && autonomia > 0 && consumoBase > 0
}
class Sonda(
    nombre: String = "Sonda",
    codigo: String = "S-001",
    fechaFab: LocalDate = LocalDate.of(2025, 1, 1),
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
    override fun cuantoConsume(planeta: Planeta, unidades: Int): Double = consumoBase + (consumoBase * 0.10 * unidades)
}

class Carguero(
    nombre: String = "Carguero",
    codigo: String = "S-003",
    fechaFab: LocalDate = LocalDate.of(1994, 1, 1),
    vel: Double = 35.0,
    aut: Double = 30.0,
    cons: Double = 10.0
) : Nave(nombre, codigo, fechaFab, vel, aut, cons) {

    override fun cuantoConsume(planeta: Planeta, unidades: Int): Double {
        val consumoConCarga = consumoBase + (consumoBase * 0.05 * unidades)
        return if (antiguedad > 10) consumoConCarga * 1.20 else consumoConCarga
    }
}