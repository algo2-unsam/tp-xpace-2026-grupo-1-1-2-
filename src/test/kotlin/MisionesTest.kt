package ar.edu.unsam.algo2

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldBeFalse
import java.time.LocalDate

class MisionesSpec : DescribeSpec({

    val baseKennedy = BaseLanzamiento()
    val baseRusa = BaseLanzamiento()

    val marte = Planetas(
        nombre = "Marte",
        temperaturaMedia = 20,
        gravedad = 10,
        nivelRad = 5,
        aguaLiquida = true,
        toxicidadAtmos = 0,
        actTectonica = 0,
        tamaño = 5000,
        fechaDesc = LocalDate.now(),
        distTierra = 100,
        aterrizado = false,
        temperatura = 25
    )

    val unRol = Piloto()
    val unPerfil = Conformista()

    describe("Validación de Misiones (XPACE)") {

        it("No debería lanzarse una misión si la nave no tiene autonomía suficiente") {
            val naveDebil = Sonda(
                nombre = "Sputnik",
                ID = 101,
                fechaFab = LocalDate.now().minusYears(2),
                velocidadProm = 10,
                autonomia = 5,
                consumoBase = 1.0,
                baseActual = baseKennedy
            )

            val mision = Misiones(
                nombre = "Misión Corta",
                descripcion = "Test de autonomía",
                fechaLanz = LocalDate.now().plusDays(10),
                naveAsig = naveDebil,
                tripulanteAsig = mutableListOf(),
                planetaAsig = marte,
                baseAsignada = baseKennedy
            )

            mision.puedeLanzarse().shouldBeFalse()
        }

        it("Una Sonda no debería permitir tripulantes (Capacidad 0)") {
            val sonda = Sonda("Voyager", 202, LocalDate.now(), 100, 10000, 1.0, baseKennedy)

            val neil = Tripulante(
                "Neil", "Armstrong", LocalDate.of(1930, 8, 5), LocalDate.now().minusYears(10),
                unRol, unPerfil, 5000.0, baseKennedy
            )

            val misionConGente = Misiones(
                "Sonda con Tripulante", "Debe fallar", LocalDate.now().plusDays(5),
                sonda, mutableListOf(neil), marte, baseKennedy
            )


            misionConGente.puedeLanzarse().shouldBeFalse()
        }

        it("La misión falla si la nave está en una base y los tripulantes en otra") {
            val naveEnKennedy = Transbordador("Discovery", 303, LocalDate.now(), 100, 5000, 10.0, 5, baseKennedy)

            val tripulanteRuso = Tripulante(
                "Yuri", "Gagarin", LocalDate.of(1934, 3, 9), LocalDate.now().minusYears(5),
                unRol, unPerfil, 4000.0, baseRusa
            )

            val misionEnKennedy = Misiones(
                "Misión USA", "Nave en Kennedy", LocalDate.now().plusDays(1),
                naveEnKennedy, mutableListOf(tripulanteRuso), marte, baseKennedy
            )

            misionEnKennedy.puedeLanzarse().shouldBeFalse()
        }

        it("Se lanza exitosamente si todo coincide") {
            val naveOk = Transbordador("Endeavour", 404, LocalDate.now(), 100, 5000, 5.0, 10, baseKennedy)
            val tripulanteOk = Tripulante("Buzz", "Aldrin", LocalDate.now().minusYears(40), LocalDate.now().minusYears(15), unRol, unPerfil, 5000.0, baseKennedy)

            val misionExito = Misiones(
                "Misión Exitosa", "Todo OK", LocalDate.now().plusDays(20),
                naveOk, mutableListOf(tripulanteOk), marte, baseKennedy
            )

            misionExito.puedeLanzarse().shouldBeTrue()

            misionExito.lanzar()
            misionExito.estado shouldBe EstadoMision.EN_CURSO
            naveOk.mision.shouldBeTrue()
        }
    }
})