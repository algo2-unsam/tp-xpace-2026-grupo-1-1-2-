package ar.edu.unsam.algo2
import java.time.LocalDate
import java.time.Period

abstract class Tripulante {
    open val nombre: String = ""
    open val apellido: String = ""
    open val fechaNac: LocalDate = LocalDate.now()
    open val edad: Int = 0
    open var misExitosa: Int = 0
    open var misFallidas: Int = 0
    open var misParcial: Int = 0
    open val fechaInicio: LocalDate = LocalDate.now()
    open var añosExp: Int = 0

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
}
class Tripulante1 : Tripulante() {
    override val nombre = "Juan"
    override val apellido = "Perez"
    override val fechaNac = LocalDate.of(2004, 8, 5)
    override val edad = 30
    override var misExitosa = 10
    override var misFallidas = 6
    override var misParcial = 12
    override val fechaInicio= LocalDate.of(2000,1,1)
    override var añosExp = 0
}

abstract class Planetas{
    open val nombre: String = ""
    open val temperaturaMedia: Int = 3
    open val gravedad : Int = 6
    open val nivelRad: Int = 50
    open val aguaLiquida: Boolean = true
    open val toxicidadAtmos: Int = 63
    open val actTectonica: Int = 9
    open val tamaño: Int = 66
    open val fechaDesc: LocalDate = LocalDate.now()
    open val distTierra: Int = 2
    open var aterrizado: Boolean = false
    open val temperatura: Int = 26

    fun tempIdeal(): Boolean{
        return temperatura > 0 && temperatura < 40
    }
    fun gravSoportable(): Boolean{
        return gravedad > 3 && gravedad < 15
    }
    fun cambiaAterrizaje(): Boolean {
        if (!aterrizado) {aterrizado = true}
        return aterrizado
    }
}
class Pluton: Planetas() {
    override val nombre = "Pluton"
}

abstract class Naves{
    open val nombre: String = ""
    open val ID: Int = 0
    open val fechaFab: LocalDate = LocalDate.now()
    open val velocidadProm: Int = 0
    open val autonomia: Int = 0
    open var consumoBase: Double = 0.0
    open var mision: Boolean = false
    open val capacidad : Int = 0
    open val tripulantes: Int = 0

    fun antiguedad(): Int {
        return Period.between(fechaFab, LocalDate.now()).years
    }
    fun cambiaEstado(): Boolean {
        mision = !mision
        return mision
    }
    open fun cuantoConsume(): Double{
        return consumoBase
    }
    fun consumoTotal(planeta: Planetas): Double {
        return cuantoConsume() * planeta.distTierra
    }
    fun alcanzaPlaneta(planeta: Planetas): Boolean{
        return ((planeta.distTierra * 365 / velocidadProm) < autonomia)
    }
    fun esModerna(): Boolean{
        return antiguedad() < 5
    }
    fun capacidadApta(): Boolean{
        if ((capacidad >= tripulantes) && (capacidad >= 1)){return true}
        else{return false}
    }
}
class Sonda: Naves(){
    override val tripulantes = 0
    override var consumoBase = 10.5
    override val fechaFab = LocalDate.of(2000,1,1)
    override val capacidad  = 0
    override val velocidadProm = 20
    override val autonomia = 600
    override var mision = false
}
class Transbordador: Naves(){
    override val tripulantes = 6
    override val capacidad = 4
    override var consumoBase = 1000.5
    override val fechaFab = LocalDate.of(2022,1,1)
    override val velocidadProm = 5
    override val autonomia = 300
    override var mision = false

    override fun cuantoConsume(): Double {
        return (consumoBase + (consumoBase * 0.10 * capacidad))
    }
}
class Carguero:Naves(){
    override val tripulantes = 5
    override val capacidad = 6
    override val fechaFab = LocalDate.of(2000,1,1)
    override var consumoBase = 48.3
    override val velocidadProm = 20
    override val autonomia = 10000
    override var mision = false

    override fun cuantoConsume(): Double {
       if (antiguedad() < 10){
           return consumoBase + (consumoBase * 0.05 * capacidad)
       }else{
           return (consumoBase + (consumoBase * 0.05 * capacidad)) * 0.2 //el 20% mas si tiene mas de 10 años de antiguedad
       }
    }
}

abstract class Misiones{
    open val nombre: String = ""
    open val descripcion: String = ""
    open val fechaLanz: LocalDate = LocalDate.now()
    open val naveAsig: Naves = Sonda()
    open val tripulanteAsig: Tripulante = Tripulante1()
    open val planetaAsig: Planetas = Pluton()
    open var estado: Int = 0
    //1 BORRADOR - 2 EN CURSO - 3 COMPLETADA - 4 FALLIDA - 5 CANCELADA

    fun duracion(planeta: Planetas, nave:Naves):Int{
        return planeta.distTierra * 365 / nave.velocidadProm * 2
    }
    fun Borrador_A_EnCurso(nave:Naves, planeta:Planetas,tripulante: Tripulante):Boolean{
        if ((estado == 1) && (nave.alcanzaPlaneta(planeta)) && nave.capacidadApta()) {
            estado = 2
            return nave.cambiaEstado()
       }else{
            tripulante.sumaMisionFallida()
            return false
       }
    }
    fun EnCurso_A_Completada(planeta: Planetas,tripulante: Tripulante,nave:Naves): Boolean{
        estado = 3
        nave.cambiaEstado()
        return ((planeta.cambiaAterrizaje()) && (tripulante.sumaMisionExitosa()))
    }
    fun altoRiesgo (planeta: Planetas, nave:Naves): Boolean{
        return ((!planeta.tempIdeal()) && (!planeta.gravSoportable()) && (duracion(planeta,nave) > 500))
    }
    fun EnCurso_A_Cancelada(planeta: Planetas,tripulante: Tripulante,nave:Naves): Boolean{
        estado = 5
        nave.cambiaEstado()
        return ((altoRiesgo(planeta, nave)) && (tripulante.sumaMisionParcial()))
    }
}
class Mision1: Misiones(){
    override val naveAsig = Transbordador()
    override val tripulanteAsig = Tripulante1()
    override val planetaAsig = Pluton()
    override var estado = 1
}
