package devnovikov.lincheck.account

class AccountUnsafe4 {

    private var balance = 0.0

    fun modify(difference: Double) {
        synchronized(balance) {
            balance += difference
        }
    }

    fun get() = balance

}