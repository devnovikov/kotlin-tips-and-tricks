package devnovikov.lincheck.problem

import devnovikov.lincheck.ModelCheckingExecutor
import org.jetbrains.kotlinx.lincheck.annotations.Operation

/**
 * = Invalid execution results =
 * Parallel part:
 * | withdraw(-0.1999998539686203): true       | withdraw(0.10000015050172806): true [1,-] |
 * | withdraw(-0.2999998554587364): true [1,-] |                                           |
 * Post part:
 * [withdraw(0.3000001534819603): false]
 *
 * ---
 * values in "[..]" brackets indicate the number of completed operations
 * in each of the parallel threads seen at the beginning of the current operation
 * ---
 *
 * = The following interleaving leads to the error =
 * Parallel part trace:
 * | withdraw(-0.1999998539686203): true                                                                                |                                                                                                                    |
 * | withdraw(-0.2999998554587364)                                                                                      |                                                                                                                    |
 * |   withdraw(-0.2999998554587364): true at BankAccountRaceConditionTest.withdraw(BankAccountRaceConditionTest.kt:12) |                                                                                                                    |
 * |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:7)           |                                                                                                                    |
 * |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)           |                                                                                                                    |
 * |     switch                                                                                                         |                                                                                                                    |
 * |                                                                                                                    | withdraw(0.10000015050172806)                                                                                      |
 * |                                                                                                                    |   withdraw(0.10000015050172806): true at BankAccountRaceConditionTest.withdraw(BankAccountRaceConditionTest.kt:12) |
 * |                                                                                                                    |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:7)           |
 * |                                                                                                                    |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)           |
 * |                                                                                                                    |     switch                                                                                                         |
 * |     balance.WRITE(0.4999997094273567) at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)          |                                                                                                                    |
 * |   result: true                                                                                                     |                                                                                                                    |
 * |   thread is finished                                                                                               |                                                                                                                    |
 * |                                                                                                                    |     balance.WRITE(0.09999970346689224) at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)         |
 * |                                                                                                                    |   result: true                                                                                                     |
 * |                                                                                                                    |   thread is finished                                                                                               |
 *
 * Detailed parallel part trace:
 * | withdraw(-0.1999998539686203)                                                                                      |                                                                                                                    |
 * |   withdraw(-0.1999998539686203): true at BankAccountRaceConditionTest.withdraw(BankAccountRaceConditionTest.kt:12) |                                                                                                                    |
 * |     balance.READ: 0.0 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:7)                          |                                                                                                                    |
 * |     balance.READ: 0.0 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)                          |                                                                                                                    |
 * |     balance.WRITE(0.1999998539686203) at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)          |                                                                                                                    |
 * |   result: true                                                                                                     |                                                                                                                    |
 * | withdraw(-0.2999998554587364)                                                                                      |                                                                                                                    |
 * |   withdraw(-0.2999998554587364): true at BankAccountRaceConditionTest.withdraw(BankAccountRaceConditionTest.kt:12) |                                                                                                                    |
 * |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:7)           |                                                                                                                    |
 * |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)           |                                                                                                                    |
 * |     switch                                                                                                         |                                                                                                                    |
 * |                                                                                                                    | withdraw(0.10000015050172806)                                                                                      |
 * |                                                                                                                    |   withdraw(0.10000015050172806): true at BankAccountRaceConditionTest.withdraw(BankAccountRaceConditionTest.kt:12) |
 * |                                                                                                                    |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:7)           |
 * |                                                                                                                    |     balance.READ: 0.1999998539686203 at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)           |
 * |                                                                                                                    |     switch                                                                                                         |
 * |     balance.WRITE(0.4999997094273567) at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)          |                                                                                                                    |
 * |   result: true                                                                                                     |                                                                                                                    |
 * |   thread is finished                                                                                               |                                                                                                                    |
 * |                                                                                                                    |     balance.WRITE(0.09999970346689224) at BankAccountRaceCondition.withdraw(BankAccountRaceCondition.kt:8)         |
 * |                                                                                                                    |   result: true                                                                                                     |
 * |                                                                                                                    |   thread is finished
 */
class BankAccountRaceConditionTest : ModelCheckingExecutor() {
    private val account = BankAccountRaceCondition()

    @Operation
    fun deposit(amount: Double) = account.deposit(amount)

    @Operation
    fun withdraw(amount: Double) = account.withdraw(amount)
}