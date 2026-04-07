import ar.edu.unsam.algo2.Pluton
import ar.edu.unsam.algo2.Tripulante1
import ar.edu.unsam.algo2.Transbordador
import ar.edu.unsam.algo2.Carguero
import ar.edu.unsam.algo2.Mision1

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Test: DescribeSpec({
    val mision = Mision1()
    val planeta = Pluton()
    val nave = Transbordador()
    val nave2 = Carguero()
    val tripulante = Tripulante1()

    describe("Dado un planeta"){
        planeta.gravedad shouldBe 6
        planeta.tempIdeal() shouldBe true
        planeta.gravSoportable() shouldBe true
    }
    describe("Dado un tripulante"){
        tripulante.misExitosa shouldBe 10
        tripulante.experiencia() shouldBe 37
        tripulante.añosActivo() shouldBe 26
    }
    describe("Dada una nave"){
        nave.alcanzaPlaneta(planeta) shouldBe true //la nave Transbordador puede alcanzar pluton
        nave.esModerna() shouldBe true
        nave2.esModerna() shouldBe false
    }
    describe("Dada una mision"){
        //verifico las misiones exitosas y fallidad del Tripulante1, el Transbordador no esta en mision y Pluton no fue aterrizado
        mision.duracion(planeta, nave) shouldBe 292
        tripulante.misExitosa shouldBe 10
        tripulante.misFallidas shouldBe 6
        nave.mision shouldBe false
        planeta.aterrizado shouldBe false

        //hago que la Mision1 vaya a Pluton con el Transbordador y el Tripulante1. Falla por lo cual se le suma 1 mision fallida y Transbordador no esta en mision
        mision.Borrador_A_EnCurso(nave, planeta, tripulante) shouldBe false
        tripulante.misFallidas shouldBe 7
        nave.mision shouldBe false

        //hago que la Mision1 vaya a Pluton con el Carguero y el Tripulante1. No falla por lo cual el Carguero esta en mision
        mision.Borrador_A_EnCurso(nave2, planeta, tripulante) shouldBe true
        nave2.mision shouldBe true

        //la Mision1 se completa, se le suma 1 mision exitosa al Tripulante 1 y Pluton fue aterrizado
        //mision.EnCurso_A_Completada(planeta,tripulante) shouldBe true
        //tripulante.misExitosa shouldBe 11
        //planeta.aterrizado shouldBe true
    }
})
