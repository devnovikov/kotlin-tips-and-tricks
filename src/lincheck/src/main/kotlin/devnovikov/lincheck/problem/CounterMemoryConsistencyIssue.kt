package devnovikov.lincheck.problem

class CounterMemoryConsistencyIssue {
    private var value = 0

    fun increment() {
        value++
    }

    fun get() = value
}