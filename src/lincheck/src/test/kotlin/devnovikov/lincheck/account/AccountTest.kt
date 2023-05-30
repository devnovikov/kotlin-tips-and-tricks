package devnovikov.lincheck.account


import org.jetbrains.kotlinx.lincheck.*
import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import org.junit.jupiter.api.Test

class AccountTest {

    /**
     * Choose any of Account classes
     *
     * thread safe:
     * [AccountSafe1], [AccountSafe2], [AccountSafe3], [AccountSafe4], [AccountSafe5], [AccountSafe6], [AccountSafe7], [AccountSafe8]
     *
     * thread unsafe:
     * [AccountUnsafe1], [AccountUnsafe2], [AccountUnsafe3], [AccountUnsafe4]
     */

    private val account = AccountSafe8()

//        use with AccountSafe6
//    @Operation(allowExtraSuspension = true)
//    suspend fun modify(diff: Double) = account.modify(diff)

    @Operation
    fun modify(diff: Double) = account.modify(diff)

    @Operation
    fun get() = account.get()

    // Run Lincheck in the stress testing mode
    @Test
    fun stressTest() = StressOptions().check(this::class)

    // Run Lincheck in the model checking testing mode
    @Test
    fun modelCheckingTest() = ModelCheckingOptions().check(this::class)
}