
# Lincheck

(**short**) _Lincheck - JetBrains library for testing concurrent algorithm and data structure._

(**long**) Lincheck, short for "Linearizability Check" is a Kotlin library designed to test the correctness of concurrent data structures. The main goal of this library is to verify if a data structure implementation adheres to the linearizability property - a key consistency condition for concurrent objects. Linearizability ensures that although operations can be executed concurrently, the resulting behavior appears as if those operations were executed sequentially, respecting the invocation order.

Developed by JetBrains Research, Lincheck provides a way for Kotlin developers to generate various concurrent test scenarios and check the implementations of synchronization algorithms and data structures in their applications. This powerful testing tool is essential for validating and avoiding common concurrency issues such as race conditions, deadlocks, and other inconsistencies that can arise in multithreaded environments.

**Some useful features of Lincheck**:

1. Test case generation: Lincheck can automatically generate a large number of test scenarios using a pre-defined set of operations and thread synchronization primitives. This significantly reduces the manual effort required to create test cases for complex concurrent data structures.

2. Execution strategies: Lincheck supports different execution strategies, such as stress testing, random switchings, and coroutine-based execution. This enables developers to test various characteristics of their code within a controlled environment and verify the desired linearizability.

3. Customization: Lincheck allows customization of the testing properties, such as the number of threads, the number of test iterations, and the scenarios’ operation sequences. This flexibility allows developers to fine-tune the testing process according to their requirements.

4. Integration: Lincheck can be easily integrated with popular testing libraries, such as JUnit and TestNG, making it easier to incorporate it into an existing project’s testing process.

5. Detailed reports: Lincheck produces detailed reports of the test results, providing information on inconsistent scenarios, incorrect results, or unexpected exceptions. This makes it much easier for developers to diagnose and fix issues within their concurrent data structures.

Overall, Lincheck is a valuable library for Kotlin developers who need to ensure the correctness and reliability of their concurrent data structures. By providing automatic test case generation and various execution strategies, Lincheck enables developers to thoroughly test their code and achieve linearizability, preventing potential issues in production.


- [Lincheck repository](https://github.com/JetBrains/lincheck)
- [Lincheck introduction](https://github.com/JetBrains/lincheck/blob/master/docs/topics/introduction.md)

## Usage
[AccountTest.kt](src%2Ftest%2Fkotlin%2Fdevnovikov%2Flincheck%2Faccount%2FAccountTest.kt)
```kotlin
class AccountTest {

    private val account = AccountSafe6()

    @Operation(allowExtraSuspension = true)
    suspend fun modify(diff: Double) = account.modify(diff)

    @Operation
    fun get() = account.get()

    // Run Lincheck in the stress testing mode
    @Test
    fun stressTest() = StressOptions().check(this::class)

    // Run Lincheck in the model checking testing mode
    @Test
    fun modelCheckingTest() = ModelCheckingOptions().check(this::class)
}
```

#### 12 implementations of Account class

##### 4 non-thread safe:
- [AccountUnsafe1.kt](src/main/kotlin/devnovikov/lincheck/account/AccountUnsafe1.kt)
- [AccountUnsafe2.kt](src/main/kotlin/devnovikov/lincheck/account/AccountUnsafe2.kt)
- [AccountUnsafe3.kt](src/main/kotlin/devnovikov/lincheck/account/AccountUnsafe3.kt)
- [AccountUnsafe4.kt](src/main/kotlin/devnovikov/lincheck/account/AccountUnsafe4.kt)

##### 8 thread-safe:
- [AccountSafe1.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe1.kt)
- [AccountSafe2.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe2.kt)
- [AccountSafe3.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe3.kt)
- [AccountSafe4.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe4.kt)
- [AccountSafe5.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe5.kt)
- [AccountSafe6.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe6.kt)
- [AccountSafe7.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe7.kt)
- [AccountSafe8.kt](src/main/kotlin/devnovikov/lincheck/account/AccountSafe8.kt)

## Concurrency problems
Below are the listed concurrency problems along with the respective class and test files. Each test case includes an example output obtained after executing the Lincheck test, which is provided in the comment section.
- **Race Condition** -> 
  - Class: [BankAccountRaceCondition.kt](src%2Fmain%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FBankAccountRaceCondition.kt) 
  - Test: [BankAccountRaceConditionTest.kt](src%2Ftest%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FBankAccountRaceConditionTest.kt)
  - <details> A race condition occurs when multiple threads or processes access shared data concurrently, and the final outcome of the program depends on the order or timing of their execution. In this case, the race condition arises due to the concurrent access to the balance variable by multiple threads. </details>
- **Memory Consistency Issue**
  - Class: [CounterMemoryConsistencyIssue.kt](src%2Fmain%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FCounterMemoryConsistencyIssue.kt)
  - Test: [CounterRaceConditionTest.kt](src%2Ftest%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FCounterRaceConditionTest.kt)
  - <details> The concurrency problem in the provided code is known as a "Memory Consistency Issue." It occurs when multiple threads access and modify shared data without proper synchronization, leading to unexpected or inconsistent results. Without proper synchronization mechanisms, such as locks or atomic operations, concurrent access to the value variable can lead to race conditions. Race conditions occur when multiple threads try to access and modify the same data simultaneously, and the final outcome depends on the timing and interleaving of their operations. </details>
- **LinkedList Inconsistent State**
    - Class: [LinkedListInconsistentState.kt](src%2Fmain%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FLinkedListInconsistentState.kt)
    - Test: [LinkedListInconsistentStateTest.kt](src%2Ftest%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FLinkedListInconsistentStateTest.kt)
    - <details> Inconsistent state occurs when multiple threads concurrently access and modify a linked list, resulting in an inconsistent or corrupted state. The inconsistency arises due to the lack of proper synchronization mechanisms when multiple threads access and modify the linked list concurrently. The add() and remove() methods involve multiple operations that manipulate the list structure, such as updating node references and modifying the size variable. When multiple threads concurrently execute these methods, race conditions can occur. For example, if two threads simultaneously attempt to add a node to the list, they may end up overwriting each other's changes, causing nodes to be lost or incorrectly linked. Similarly, concurrent removal of nodes can lead to inconsistent node references and incorrect list size. The lack of synchronization in the provided code leaves the linked list in an inconsistent state, making it prone to data corruption and incorrect results.  </details>
- **Deadlock**
    - Class: [DeadLockResource.kt](src%2Fmain%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FDeadLockResource.kt)
    - Test: [DeadLockResourceTest.kt](src%2Ftest%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FDeadLockResourceTest.kt)
    - <details> Deadlock occurs when two or more threads are blocked indefinitely, waiting for each other to release resources that they hold. The problem arises when multiple threads try to execute the work() method concurrently. Each thread attempts to acquire the locks on first and second in the specified order. However, if two threads acquire the locks in different orders simultaneously, a deadlock situation can occur. To prevent deadlocks, it is crucial to ensure that threads always acquire locks in a consistent and predictable order. Additionally, deadlock detection and avoidance techniques can be employed, such as using timeouts, resource ordering, or employing higher-level concurrency abstractions that handle synchronization automatically.</details>
- **Livelock**
    - Class: [LiveLockResource.kt](src%2Fmain%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FLiveLockResource.kt)
    - Test: [LiveLockResourceTest.kt](src%2Ftest%2Fkotlin%2Fdevnovikov%2Flincheck%2Fproblem%2FLiveLockResourceTest.kt)
    - <details> Livelock occurs when multiple threads are unable to make progress and constantly change their states or perform actions in response to each other's actions, without achieving the desired outcome.The problem arises when both threads enter the performAction() method simultaneously. If the value passed to the method is even, the first thread sets locked1 to true and enters a while loop, continuously checking the value of locked2. The first thread is waiting for the second thread to unlock locked2 before proceeding further. However, the second thread also enters the performAction() method and sets locked2 to true, entering its own while loop, waiting for the first thread to unlock locked1. At this point, both threads are actively waiting for each other to unlock their respective locks. They are in a constant loop, changing their states in response to each other's actions, but they are unable to progress towards releasing the locks and completing the execution. This situation results in a livelock, where the threads are "alive" and actively performing actions, but they are unable to make any forward progress. The program becomes stuck in a state of constant state changes and actions, without achieving the intended outcome. Livelocks are problematic because they can cause significant resource wastage and prevent the execution of critical tasks. Resolving livelocks often requires introducing additional logic or strategies to break the impasse and allow the threads to make progress. This can involve introducing randomness, timeouts, or changing the synchronization logic to ensure forward progress.</details>

## Authors

- [Alexey Novikov](https://www.linkedin.com/in/devnovikov)

## 🔗 Links
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/devnovikov)
