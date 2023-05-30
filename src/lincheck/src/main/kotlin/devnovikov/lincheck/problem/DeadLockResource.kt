package devnovikov.lincheck.problem


class DeadLockResource(private val first: Any, private val second: Any) {
    private var counter = 0

    fun work() {
        synchronized(first) {
            synchronized(second) {
                counter++
            }
        }
    }
}