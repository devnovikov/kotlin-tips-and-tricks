package devnovikov.lincheck.account

import java.util.concurrent.Semaphore

class AccountSafe7 {
    private var balance = 0.0
    private val semaphore = Semaphore(1)

    fun modify(difference: Double) {
        semaphore.acquireUninterruptibly()
        try {
            balance += difference
        } finally {
            semaphore.release()
        }
    }

    fun get(): Double = balance
}