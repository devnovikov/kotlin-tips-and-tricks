package devnovikov.lincheck.problem

import devnovikov.lincheck.ModelCheckingExecutor
import org.jetbrains.kotlinx.lincheck.annotations.Operation

/**
 * = Invalid execution results =
 * Parallel part:
 * | increment(): void | increment(): void |
 * Post part:
 * [get(): 1]
 *
 * = The following interleaving leads to the error =
 * Parallel part trace:
 * |                      | increment()                                                                         |
 * |                      |   increment() at CounterRaceConditionTest.increment(CounterRaceConditionTest.kt:13) |
 * |                      |     value.READ: 0 at CounterRaceCondition.increment(CounterRaceCondition.kt:7)      |
 * |                      |     switch                                                                          |
 * | increment(): void    |                                                                                     |
 * |   thread is finished |                                                                                     |
 * |                      |     value.WRITE(1) at CounterRaceCondition.increment(CounterRaceCondition.kt:7)     |
 * |                      |   result: void                                                                      |
 * |                      |   thread is finished                                                                |
 *
 * Detailed parallel part trace:
 * |                                                                                     | increment()                                                                         |
 * |                                                                                     |   increment() at CounterRaceConditionTest.increment(CounterRaceConditionTest.kt:13) |
 * |                                                                                     |     value.READ: 0 at CounterRaceCondition.increment(CounterRaceCondition.kt:7)      |
 * |                                                                                     |     switch                                                                          |
 * | increment()                                                                         |                                                                                     |
 * |   increment() at CounterRaceConditionTest.increment(CounterRaceConditionTest.kt:13) |                                                                                     |
 * |     value.READ: 0 at CounterRaceCondition.increment(CounterRaceCondition.kt:7)      |                                                                                     |
 * |     value.WRITE(1) at CounterRaceCondition.increment(CounterRaceCondition.kt:7)     |                                                                                     |
 * |   result: void                                                                      |                                                                                     |
 * |   thread is finished                                                                |                                                                                     |
 * |                                                                                     |     value.WRITE(1) at CounterRaceCondition.increment(CounterRaceCondition.kt:7)     |
 * |                                                                                     |   result: void                                                                      |
 * |                                                                                     |   thread is finished                                                                |
 */

class CounterRaceConditionTest : ModelCheckingExecutor() {
    private val counter = CounterMemoryConsistencyIssue()

    @Operation
    fun increment() = counter.increment()

    @Operation
    fun get() = counter.get()
}