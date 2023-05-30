package devnovikov.lincheck.account

class AccountSafe8 {
    private val balance = DoubleWrapper(0.0)

    fun modify(difference: Double) {
        synchronized(balance) {
            balance.value += difference
        }
    }

    fun get(): Double {
        return balance.value
    }

    private class DoubleWrapper(var value: Double)
}