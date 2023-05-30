package devnovikov.lincheck

import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

open class ModelCheckingExecutor {
    @Test
    fun modelCheckingTest() {
        var capturedErrorMessage: String? = null

        runCatching {
            ModelCheckingOptions().check(this::class)
        }.onFailure {
            capturedErrorMessage = it.message
        }

        System.err.println(capturedErrorMessage)
        assertNotNull(capturedErrorMessage, "The onFailure block should have been executed")
    }
}