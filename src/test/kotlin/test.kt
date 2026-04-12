package ar.edu.unsam.algo2
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import org.uqbar.geodds.Point

class Test : DescribeSpec({

    val comandante = Comandante()
    val conformista = Conformista()
    val direccion1 = Direccion(ubiGeo = Point(100.0, 300.0))
    val direccion2 = Direccion(ubiGeo = Point(101.0, 301.0))
    val base = BaseLanzamiento(direccion = direccion1)
    val base2 = BaseLanzamiento(direccion = direccion2)
    val tripulante1 = Tripulante("Nicolas","Perez",20.0,comandante,conformista,
        LocalDate.of(1998, 5, 14),LocalDate.of(2005, 5, 14),base,
        2,3,0   )

    val tripulante2 = Tripulante("Matias","Perez",20.0,comandante,conformista,
        LocalDate.of(1995, 5, 14),LocalDate.of(2003, 5, 14),base2,
        1,3,4   )

    val pluton = Planeta()
    val naveTransbordador = Transbordador()
    val naveCarguero = Carguero()
    val naveSonda = Sonda()
    val mision1 = Mision(nave= naveTransbordador)
    val mision2 = Mision(nave= naveCarguero)
    mision1.planeta = pluton
    mision2.planeta = pluton
    mision1.tripulantes.add(tripulante1)
    mision2.tripulantes.add(tripulante2)


    describe("TRIPULANTE") {
        tripulante1.esAptoPara(mision1) shouldBe true
        tripulante1.salarioTotal() shouldBe 32 // ver
        tripulante1.baseCercana(base2,200.0) shouldBe true //ver

        it("experiencia correcta") {
            val tripulante1 = Tripulante(misExitosa = 4, misFallidas = 2, misParcial = 4)
            tripulante1.experiencia() shouldBe (tripulante1.añosActivo + 2 + 1 + 1)
        }
    }

    describe("PLANETA"){
        pluton.tempIdeal() shouldBe true
        pluton.gravSoportable() shouldBe true
        pluton.esHabitable() shouldBe false
        pluton.indicePeligrosidad() shouldBe 15
        pluton.esExplorable() shouldBe true
    }

    describe("NAVES"){
        naveSonda.antiguedad shouldBe (LocalDate.now().year - 2025)
        naveTransbordador.antiguedad shouldBe (LocalDate.now().year - 1990)
        naveCarguero.antiguedad shouldBe (LocalDate.now().year - 1994)

        naveSonda.esModerna() shouldBe true
        naveCarguero.esModerna() shouldBe false
        naveTransbordador.esModerna() shouldBe false

        naveSonda.puedeAlcanzar(pluton) shouldBe true
        naveCarguero.puedeAlcanzar(pluton) shouldBe false
        naveTransbordador.puedeAlcanzar(pluton) shouldBe true

        naveSonda.consumoTotal(pluton,0) shouldBe 20.0
        naveCarguero.consumoTotal(pluton,2) shouldBe 26.4
        naveTransbordador.consumoTotal(pluton,6) shouldBe 32

        naveSonda.esValida() shouldBe true
        naveCarguero.esValida() shouldBe true
        naveTransbordador.esValida() shouldBe true

        it("autonomia justa para llegar a destino") {
            val planeta = Planeta(distTierra = 2.0)
            val nave = Sonda(vel = 100.0, aut = 14.6)

            nave.puedeAlcanzar(planeta) shouldBe true
        }
    }

    describe("MISIONES"){

        it("CON NAVE TRANSBORDADOR"){
            mision2.duracionEstimada().toInt() shouldBe 41
            mision1.altoRiesgo() shouldBe false

            mision1.planeta = pluton
            base.navesEstacionadas.add(naveTransbordador)
            mision1.preLanzamiento() shouldBe true
            mision1.lanzar()
            mision1.estado shouldBe EstadoMision.EN_CURSO
            naveTransbordador.enMision shouldBe true

            mision1.completar()
            mision1.estado shouldBe EstadoMision.COMPLETADA
            pluton.aterrizado shouldBe true
            tripulante1.misExitosa shouldBe 3
            naveTransbordador.enMision shouldBe false
        }
        it("CON NAVE CARGUERO"){
            mision2.duracionEstimada() shouldBe 41.714285714285715 //ver de redondear
            mision2.altoRiesgo() shouldBe false

            mision2.preLanzamiento() shouldBe false //xq carguero no puede alcanzar pluton
            mision2.fallar()
            mision2.estado shouldBe EstadoMision.FALLIDA
            tripulante2.misFallidas shouldBe 4
            naveCarguero.enMision shouldBe false
        }
        it("no lanzar si la nave no esta estacionada") {
            val base = BaseLanzamiento(direccion = direccion1)
            val nave = Transbordador()
            val trip = Tripulante(baseAsignada = base)
            val mision = Mision(nave = nave)

            mision.planeta = pluton
            mision.tripulantes.add(trip)

            mision.preLanzamiento() shouldBe false
        }
        it("lanzar si la nave esta en la base") {
            val base = BaseLanzamiento(direccion = direccion1)
            val nave = Transbordador()
            base.navesEstacionadas.add(nave)
            val trip = Tripulante(baseAsignada = base)

            val mision = Mision(nave = nave)
            mision.planeta = pluton
            mision.tripulantes.add(trip)

            mision.preLanzamiento() shouldBe true
        }
        it("no lanzar si los tripulantes estan en otras bases") {
            val nave = Transbordador()
            val t1 = Tripulante(baseAsignada = base)
            val t2 = Tripulante(baseAsignada = base2)

            base.navesEstacionadas.add(nave)
            val mision = Mision(nave = nave)
            mision.planeta = pluton
            mision.tripulantes.addAll(listOf(t1, t2))

            mision.preLanzamiento() shouldBe false
        }

        it("fallar cambia el estado a FALLIDA siempre") {
            val mision = Mision()
            mision.fallar()

            mision.estado shouldBe EstadoMision.FALLIDA
        }

    }


    describe("ROLES") {
        it("Ingeniero") {
            val ingConCarguero = Ingeniero(ultimaMisionFueCarguero = true)
            val ingSinCarguero = Ingeniero(ultimaMisionFueCarguero = false)

            val tripulante = Tripulante(salarioBase = 100.0, rol = ingConCarguero)
            tripulante.salarioTotal() shouldBe 140.0

            tripulante.rol = ingSinCarguero
            tripulante.salarioTotal() shouldBe 120.0
        }

        it("Científico") {
            val cientifico = Cientifico(planetasAterrizadosHistorial = 3)
            val tripulante = Tripulante(salarioBase = 100.0, rol = cientifico)

            tripulante.salarioTotal() shouldBe 130.0
        }

        it("Médico") {
            val medico = Medico()
            val tripulante = Tripulante(salarioBase = 100.0, rol = medico, misFallidas = 5)

            tripulante.salarioTotal() shouldBe 135.0
        }
    }


    describe("PERFILES DE APTITUD") {
        val martePeligroso = Planeta(temperatura = 100, nivelRad = 80)
        val misionCorta = Mision(nave = Sonda()).apply { planeta = martePeligroso }

        it("Prudente") {
            val tripulante = Tripulante(perfil = Prudente())
            tripulante.esAptoPara(misionCorta) shouldBe false
        }

        it("Cauteloso") {
            val tripulante = Tripulante(perfil = Cauteloso(umbralRadiacionMax = 50))
            tripulante.esAptoPara(misionCorta) shouldBe false
        }

        it("Exigente con nave") {
            val naveVieja = Transbordador(fechaFab = LocalDate.of(1980, 1, 1))
            val misionConNaveVieja = Mision(nave = naveVieja)
            val tripulante = Tripulante(perfil = ExigenteConNave())

            tripulante.esAptoPara(misionConNaveVieja) shouldBe false
        }
        it("planeta ya explorado, explorador no es apto") {
            val planeta = Planeta()
            planeta.aterrizado = true
            val mision = Mision().apply { this.planeta = planeta }
            val tripulante = Tripulante(perfil = Explorador())

            tripulante.esAptoPara(mision) shouldBe false
        }
    }

    describe("RESTRICCIONES DE MISIÓN") {
        it("Excede capacidad la nave") {
            val naveChica = Transbordador(capacidadMax = 1)
            val mision = Mision(nave = naveChica)
            mision.tripulantes.add(Tripulante()) //1
            mision.tripulantes.add(Tripulante()) //2

            mision.preLanzamiento() shouldBe false
        }

        it("Misión de alto riesgo se puede cancelar") {
            val planetaHostil = Planeta(aguaLiquida = false, temperatura = 200)
            val naveLenta = Sonda(vel = 1.0)
            val mision = Mision(nave = naveLenta).apply {
                planeta = planetaHostil
                estado = EstadoMision.EN_CURSO
            }

            mision.altoRiesgo() shouldBe true
            mision.cancelar()
            mision.estado shouldBe EstadoMision.CANCELADA
        }
    }

})