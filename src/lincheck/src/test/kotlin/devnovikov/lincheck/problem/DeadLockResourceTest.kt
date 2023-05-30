package devnovikov.lincheck.problem

import devnovikov.lincheck.ModelCheckingExecutor
import org.jetbrains.kotlinx.lincheck.annotations.Operation

/**
 * = The execution has hung, see the thread dump =
 * Execution scenario (parallel part):
 * | user1Work() | user2Work() |
 *
 * Thread-1:
 * 	java.lang.Thread.dumpThreads(Native Method)
 * 	java.lang.Thread.getAllStackTraces(Thread.java:1662)
 * 	devnovikov.lincheck.problem.DeadLockResource.work(DeadLockResource.kt:9)
 * 	devnovikov.lincheck.problem.DeadLockResourceTest.user2Work(DeadLockResourceTest.kt:15)
 * 	java.lang.Thread.run(Thread.java:833)
 * Thread-0:
 * 	java.lang.Thread.yield(Native Method)
 * 	devnovikov.lincheck.problem.DeadLockResource.work(DeadLockResource.kt:9)
 * 	devnovikov.lincheck.problem.DeadLockResourceTest.user1Work(DeadLockResourceTest.kt:12)
 * 	java.lang.Thread.run(Thread.java:833)
 *
 * = The following interleaving leads to the error =
 * Parallel part trace:
 * |                                                                        | user2Work()                                                            |
 * |                                                                        |   work() at DeadLockResourceTest.user2Work(DeadLockResourceTest.kt:15) |
 * |                                                                        |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:8)       |
 * |                                                                        |     switch                                                             |
 * | user1Work()                                                            |                                                                        |
 * |   work() at DeadLockResourceTest.user1Work(DeadLockResourceTest.kt:12) |                                                                        |
 * |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:8)       |                                                                        |
 * |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:9)       |                                                                        |
 * |     switch (reason: lock is already acquired)                          |                                                                        |
 * |                                                                        |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:9)       |
 * |                                                                        |     switch (reason: lock is already acquired)                          |
 *
 * Detailed parallel part trace:
 * |                                                                        | user2Work()                                                            |
 * |                                                                        |   work() at DeadLockResourceTest.user2Work(DeadLockResourceTest.kt:15) |
 * |                                                                        |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:8)       |
 * |                                                                        |     switch                                                             |
 * | user1Work()                                                            |                                                                        |
 * |   work() at DeadLockResourceTest.user1Work(DeadLockResourceTest.kt:12) |                                                                        |
 * |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:8)       |                                                                        |
 * |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:9)       |                                                                        |
 * |     switch (reason: lock is already acquired)                          |                                                                        |
 * |                                                                        |     MONITORENTER at DeadLockResource.work(DeadLockResource.kt:9)       |
 * |                                                                        |     switch (reason: lock is already acquired)                          |
 * All threads are in deadlock
 */
class DeadLockResourceTest : ModelCheckingExecutor() {
    private val r1 = Any()
    private val r2 = Any()
    private val user1 = DeadLockResource(r1, r2)
    private val user2 = DeadLockResource(r2, r1)

    @Operation
    fun user1Work() = user1.work()

    @Operation
    fun user2Work() = user2.work()
}