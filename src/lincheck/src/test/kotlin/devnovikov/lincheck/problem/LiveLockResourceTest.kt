package devnovikov.lincheck.problem

import devnovikov.lincheck.ModelCheckingExecutor
import org.jetbrains.kotlinx.lincheck.annotations.Operation

/**
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     switch (reason: active lock detected)                                         |
 * |     locked2.READ: true at LiveLockResource.performAction(LiveLockResource.kt:11)  |                                                                                   |
 * |     locked2.READ: true at LiveLockResource.performAction(LiveLockResource.kt:11)  |                                                                                   |
 * |     locked2.READ: true at LiveLockResource.performAction(LiveLockResource.kt:11)  |                                                                                   |
 * |     switch (reason: active lock detected)                                         |                                                                                   |
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     locked1.READ: true at LiveLockResource.performAction(LiveLockResource.kt:17)  |
 * |                                                                                   |     switch (reason: active lock detected)                                         |
 * |     locked2.READ: true at LiveLockResource.performAction(LiveLockResource.kt:11)  |                                                                                   |
 * |     locked2.READ: true at LiveLockResource.performAction(LiveLockResource.kt:11)  |                                                                                   |
 * |     locked2.READ: true at LiveLockResource.performAction(LiveLockResource.kt:11)  |                                                                                   |
 * All threads are in deadlock
 */

class LiveLockResourceTest : ModelCheckingExecutor() {
    private val user = LiveLockResource()

    @Operation
    fun userWork(value: Int) = user.performAction(value)
}