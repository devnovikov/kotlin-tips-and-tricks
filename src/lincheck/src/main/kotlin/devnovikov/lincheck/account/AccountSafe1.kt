package devnovikov.lincheck.account

class AccountSafe1 {

    private var balance = 0.0

    @Synchronized
    fun modify(difference: Double) {
        balance += difference
    }

    fun get() = balance

}