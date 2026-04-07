package ar.edu.unsam.algo2
import ar.edu.unsam.algo2.Conformista
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Period
import org.uqbar.geodds.Point
import java.math.BigDecimal

class Test : DescribeSpec({

    val comandante = Comandante()
    val conformista = Conformista()

    val tripulante1 = Tripulante("Nico","Perez",20.4,comandante, conformista)
    val planeta = Pluton()
    val nave = Sonda()
    val nave2 = Transbordador()
    val nave3 = Carguero()
    val mision = Mision()
    val base = BaseLanus()

    var tripulantes: MutableList<Tripulante> = mutableListOf() // repito aca ?
    val navesEstacionadas = mutableListOf<Nave>()


    //val tripulante2 = Tripulante()
    //val tripulante3 = Tripulante()
    tripulantes.add(tripulante1)
    navesEstacionadas.add(nave2)
    //tripulantes.add(tripulante2)
    //tripulantes.add(tripulante3)





    describe("NAVE") {
        nave.antiguedad shouldBe 26
        //nave2.consumoTotal(Pluton(),5) shouldBe 15.3 redondea mal
        nave3.cuantoConsume(Pluton(),2) shouldBe 5.202
        nave2.puedeAlcanzar(Pluton())
    }
    describe("MISION"){
        mision.duracionEstimada() shouldBe 14.6
        mision.altoRiesgo() shouldBe false

        mision.estado shouldBe EstadoMision.BORRADOR
        mision.lanzar()
        mision.estado shouldBe EstadoMision.EN_CURSO

        mision.completar()
        mision.estado shouldBe EstadoMision.COMPLETADA
        //planeta.aterrizado shouldBe true
        tripulante1.misExitosa shouldBe 2




    }





})
