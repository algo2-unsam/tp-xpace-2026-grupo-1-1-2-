package ar.edu.unsam.algo2

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import java.time.LocalDate

class TripulanteTest : DescribeSpec({

    // --- SETUP GENERAL ---
    val baseKennedy = BaseLanzamiento()
    val unRol = Piloto()
    val unPerfil = Conformista()

    describe("Validaciones de integridad de un Tripulante") {

        it("un tripulante con todos los datos correctos es válido") {
            val tripulanteOk = Tripulante(
                nombre = "Neil",
                apellido = "Armstrong",
                fechaNac = LocalDate.of(1930, 8, 5),
                fechaInicio = LocalDate.now(),
                rol = unRol,
                perfil = unPerfil,
                salarioBase = 5000.0,
                baseAsignada = baseKennedy
            )

            tripulanteOk.esValido().shouldBeTrue()
        }

        it("no es válido si el nombre está vacío o tiene solo espacios") {
            val tripulanteMal = Tripulante(
                nombre = "   ",
                apellido = "Aldrin",
                fechaNac = LocalDate.of(1930, 1, 20),
                fechaInicio = LocalDate.now(),
                rol = unRol,
                perfil = unPerfil,
                salarioBase = 1000.0,
                baseAsignada = baseKennedy
            )

            tripulanteMal.esValido().shouldBeFalse()
        }

        it("no es válido si el apellido está vacío") {
            val tripulanteMal = Tripulante(
                nombre = "Michael",
                apellido = "",
                fechaNac = LocalDate.of(1930, 10, 31),
                fechaInicio = LocalDate.now(),
                rol = unRol,
                perfil = unPerfil,
                salarioBase = 1000.0,
                baseAsignada = baseKennedy
            )

            tripulanteMal.esValido().shouldBeFalse()
        }

        it("no es válido si la fecha de nacimiento es hoy (debe ser anterior)") {
            val tripulanteMal = Tripulante(
                nombre = "Futuro",
                apellido = "Astronauta",
                fechaNac = LocalDate.now(),
                fechaInicio = LocalDate.now(),
                rol = unRol,
                perfil = unPerfil,
                salarioBase = 1000.0,
                baseAsignada = baseKennedy
            )

            tripulanteMal.esValido().shouldBeFalse()
        }

        it("no es válido si la fecha de nacimiento es en el futuro") {
            val tripulanteMal = Tripulante(
                nombre = "George",
                apellido = "Jetson",
                fechaNac = LocalDate.now().plusYears(100),
                fechaInicio = LocalDate.now(),
                rol = unRol,
                perfil = unPerfil,
                salarioBase = 1000.0,
                baseAsignada = baseKennedy
            )

            tripulanteMal.esValido().shouldBeFalse()
        }
    }
})