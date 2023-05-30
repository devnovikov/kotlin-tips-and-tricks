package devnovikov.lincheck.problem

import devnovikov.lincheck.ModelCheckingExecutor
import org.jetbrains.kotlinx.lincheck.annotations.Operation

/**
 * = Invalid execution results =
 * Parallel part:
 * | add(3): void | add(2): void |
 * Post part:
 * [getSize(): 1]
 *
 * = The following interleaving leads to the error =
 * Parallel part trace:
 * |                      | add(2)                                                                                    |
 * |                      |   add(2) at LinkedListInconsistentStateTest.add(LinkedListInconsistentStateTest.kt:9)     |
 * |                      |     getNext(): null at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:9)  |
 * |                      |     setNext(Node@1) at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:10) |
 * |                      |     size.READ: 0 at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:11)    |
 * |                      |     switch                                                                                |
 * | add(3): void         |                                                                                           |
 * |   thread is finished |                                                                                           |
 * |                      |     size.WRITE(1) at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:11)   |
 * |                      |   result: void                                                                            |
 * |                      |   thread is finished                                                                      |
 *
 * Detailed parallel part trace:
 * |                                                                                                         | add(2)                                                                                                  |
 * |                                                                                                         |   add(2) at LinkedListInconsistentStateTest.add(LinkedListInconsistentStateTest.kt:9)                   |
 * |                                                                                                         |     getNext(): null at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:9)                |
 * |                                                                                                         |       next.READ: null at LinkedListInconsistentState$Node.getNext(LinkedListInconsistentState.kt:29)    |
 * |                                                                                                         |     setNext(Node@1) at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:10)               |
 * |                                                                                                         |       next.WRITE(Node@1) at LinkedListInconsistentState$Node.setNext(LinkedListInconsistentState.kt:29) |
 * |                                                                                                         |     size.READ: 0 at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:11)                  |
 * |                                                                                                         |     switch                                                                                              |
 * | add(3)                                                                                                  |                                                                                                         |
 * |   add(3) at LinkedListInconsistentStateTest.add(LinkedListInconsistentStateTest.kt:9)                   |                                                                                                         |
 * |     getNext(): Node@1 at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:9)              |                                                                                                         |
 * |       next.READ: Node@1 at LinkedListInconsistentState$Node.getNext(LinkedListInconsistentState.kt:29)  |                                                                                                         |
 * |     setNext(Node@2) at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:10)               |                                                                                                         |
 * |       next.WRITE(Node@2) at LinkedListInconsistentState$Node.setNext(LinkedListInconsistentState.kt:29) |                                                                                                         |
 * |     size.READ: 0 at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:11)                  |                                                                                                         |
 * |     size.WRITE(1) at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:11)                 |                                                                                                         |
 * |   result: void                                                                                          |                                                                                                         |
 * |   thread is finished                                                                                    |                                                                                                         |
 * |                                                                                                         |     size.WRITE(1) at LinkedListInconsistentState.add(LinkedListInconsistentState.kt:11)                 |
 * |                                                                                                         |   result: void                                                                                          |
 * |                                                                                                         |   thread is finished                                                                                    |
 */

class LinkedListInconsistentStateTest : ModelCheckingExecutor() {
    private val list = LinkedListInconsistentState()

    @Operation
    fun add(value: Int) = list.add(value)

    @Operation
    fun remove(value: Int) = list.remove(value)

    @Operation
    fun getSize() = list.getSize()
}