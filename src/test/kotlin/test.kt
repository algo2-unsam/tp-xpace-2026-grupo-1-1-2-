package ar.edu.unsam.algo2

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class Test : DescribeSpec({

    val comandante = Comandante()
    val conformista = Conformista()
    val base = BaseLanus()

    val tripulante1 = Tripulante("Nico", "Perez", 10.0, comandante, conformista).apply {
        baseAsignada = base
    }

    val planeta = Pluton() // distTierra = 2.0

    val naveTransbordador = Transbordador(cons = 10.0)
    val naveCarguero = Carguero(cons = 10.0)

    val mision = Mision().apply {
        this.nave = naveTransbordador
        this.planeta = planeta
        this.tripulantes.add(tripulante1)
    }

    describe("Pruebas de NAVE") {
        it("El carguero debería dar 13.2") {
            naveCarguero.cuantoConsume(planeta, 2) shouldBe 13.2
        }

        it("El transbordador debería dar 22.0") {
            naveTransbordador.consumoTotal(planeta, 1) shouldBe 22.0
        }
    }

    describe("Pruebas de MISION") {
        it("Debería completar el ciclo de vida correctamente") {

            base.navesEstacionadas.add(naveTransbordador)
            tripulante1.baseAsignada = base
            mision.nave = naveTransbordador
            mision.tripulantes.clear()
            mision.tripulantes.add(tripulante1)

            mision.estado shouldBe EstadoMision.BORRADOR

            mision.lanzar() shouldBe true
            mision.estado shouldBe EstadoMision.EN_CURSO

            mision.completar() shouldBe true
            mision.estado shouldBe EstadoMision.COMPLETADA

            tripulante1.misExitosa shouldBe 2
        }

        it("Una misión nueva a Plutón no debería ser de alto riesgo") {
            mision.altoRiesgo() shouldBe false
        }
    }
})