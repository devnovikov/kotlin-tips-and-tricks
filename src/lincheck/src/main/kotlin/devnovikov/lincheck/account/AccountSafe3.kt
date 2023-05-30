package devnovikov.lincheck.account

import java.util.concurrent.atomic.AtomicReference

class AccountSafe3 {

    private val balance = AtomicReference(0.0)

    fun modify(difference: Double) {
        balance.updateAndGet {
            it + difference
        }
    }

    fun get(): Double = balance.get()

}