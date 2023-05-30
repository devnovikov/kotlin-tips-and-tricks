package devnovikov.lincheck.account

class AccountUnsafe1 {

    private var balance = 0.0

    fun modify(difference: Double) {
        balance += difference
    }

    fun get() = balance

}