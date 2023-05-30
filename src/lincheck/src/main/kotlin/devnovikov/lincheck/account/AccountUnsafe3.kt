package devnovikov.lincheck.account

import java.util.concurrent.atomic.AtomicReference

class AccountUnsafe3 {

    private val balance = AtomicReference(0.0)

    fun modify(difference: Double) {
        balance.set(balance.get() + difference)
    }

    fun get(): Double = balance.get()

}