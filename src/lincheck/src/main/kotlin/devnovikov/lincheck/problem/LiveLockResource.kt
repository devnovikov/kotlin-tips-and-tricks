package devnovikov.lincheck.problem


class LiveLockResource {
    private var locked1 = false
    private var locked2 = false

    fun performAction(value: Int) {
        if (value % 2 == 0) {
            locked1 = true
            while (locked2) {
                // Waiting for thread1 to unlock
            }
            locked1 = false
        } else {
            locked2 = true
            while (locked1) {
                // Waiting for thread2 to unlock
            }
            locked2 = false
        }
    }
}