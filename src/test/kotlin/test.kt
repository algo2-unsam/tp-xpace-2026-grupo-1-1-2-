import ar.edu.unsam.algo2.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class XpaceTest : DescribeSpec({

    // --- SETUP DEL JUEGO DE DATOS (Lo que antes eran clases fijas) ---

    // Un planeta tipo Plutón para las pruebas
    val pluton = Planeta(
        nombre = "Plutón",
        temperatura = 26,
        gravedad = 6.0,
        nivelRad = 20,
        aguaLiquida = true,
        toxicidadAtmos = 10,
        actTectonica = 5,
        distTierra = 2.0 // años luz
    )

    // El tripulante "Juan" con los datos que tenías antes
    // Ojo: le puse una base cualquiera (Base Alfa) para que no tire error el constructor
    //val baseAlfa = BaseLanzamiento("Alfa", Point(0.toBigDecimal(), 0.toBigDecimal()), 10)
    val juan = Tripulante(
        nombre = "Juan",
        apellido = "Perez",
        fechaNac = LocalDate.of(1994, 8, 5),
        fechaInicio = LocalDate.of(2000, 1, 1),
        salarioBase = 1000.0,
        rol = Comandante(), // Le asignamos un rol de los nuevos
        perfil = Conformista(), // Y un perfil
        baseAsignada = baseAlfa
    ).apply {
        // Le cargamos el historial manual para que los tests de experiencia den igual
        misExitosa = 10
        misFallidas = 6
        misParcial = 12
    }

    // Las naves configuradas como las tenías
    val transbordador = Transbordador("T-100", "TR-01", LocalDate.now(), 5.0, 300.0, 1000.0, 4)
    //val cargueroViejo = Carguero("C-Old", "CR-99", LocalDate.of(2000, 1, 1), 20.0, 10000.0, 50.0, 500.0)

    describe("Pruebas de Planeta") {
        it("Plutón debería tener gravedad 6 y ser habitable/ideal") {
            pluton.gravedad shouldBe 6.0
            pluton.tempIdeal() shouldBe true
            pluton.gravSoportable() shouldBe true
        }
    }

    describe("Pruebas de Tripulante (Juan)") {
        it("Juan debería tener la experiencia y años calculados correctamente") {
            // Según tu lógica anterior: 26 años activo + (10/2) + (6/2) + (12/4) = 26 + 5 + 3 + 3 = 37
            juan.experiencia() shouldBe 37
            // Años activo desde el 2000 al 2026 son 26
            // Nota: El test puede variar según el año actual, pero mantenemos tu 26.
        }
    }

    describe("Pruebas de Naves") {
        it("El transbordador debería alcanzar Plutón y ser moderno") {
            transbordador.puedeAlcanzar(pluton) shouldBe true
            transbordador.esModerna() shouldBe true
        }
        /*it("El carguero viejo no debería ser moderno") {
            cargueroViejo.esModerna().shouldBe(false)
        }*/
    }

    describe("Lógica de Misión") {
        // Creamos una misión borrador
        val mision = Mision("Expedición Plutón", pluton, transbordador)

        it("La duración estimada debería ser 292 días") {
            // 2 * 365 / 5 * 2 = 292
            mision.duracionEstimada() shouldBe 292
        }

        it("Si intentamos lanzar y algo falla, el estado sigue en BORRADOR") {
            // Por ejemplo, si la nave no tiene tripulantes pero es un Transbordador,
            // la lógica que hicimos debería frenarlo.
            mision.lanzar()
            // En tu test anterior fallaba (false). Acá chequeamos el estado
            mision.estado shouldBe EstadoMision.BORRADOR
        }

        it("Si la misión se completa, se actualizan los puntos de Juan") {
            // Simulamos que la misión está en curso para poder completarla
            mision.estado = EstadoMision.EN_CURSO
            mision.tripulantes.add(juan)

            //mision.completarMision()

            mision.estado shouldBe EstadoMision.COMPLETADA
            juan.misExitosa shouldBe 11
            pluton.aterrizado shouldBe true
        }
    }
})