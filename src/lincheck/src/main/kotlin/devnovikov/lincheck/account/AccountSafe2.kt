package devnovikov.lincheck.account

class AccountSafe2 {

    private var balance = 0.0
    private val lock = Any()

    fun modify(difference: Double) {
        synchronized(lock) {
            balance += difference
        }
    }

    fun get() = balance

}