package ar.edu.unsam.algo2
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Test : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe(name="Test prueba") {
        val nave1 = Sonda()
    }
})
