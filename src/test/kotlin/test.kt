package ar.edu.unsam.algo2
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Test : DescribeSpec({

    //defino una variable para cada clase xq sino a veces fallan los test
    val tripulante = Tripulante1()
    val planeta = Pluton()
    val sonda = Sonda()
    val nave = Transbordador()
    val nave2 = Carguero()
    val mision = Mision1()

    describe("Dado un tripulante") {
        it("cumple sus condiciones") {
            tripulante.misExitosa shouldBe 10
            tripulante.experiencia() shouldBe 37
            tripulante.aniosActivo() shouldBe 26
        }
    }
    describe("Dado un planeta") {
        it("cumple sus condiciones") {
            planeta.gravedad shouldBe 6
            planeta.tempIdeal() shouldBe true
            planeta.gravSoportable() shouldBe true
        }
    }
})
