package devnovikov.lincheck.account

import java.util.concurrent.locks.StampedLock

class AccountSafe5 {
    private var balance = 0.0
    private val lock = StampedLock()

    fun modify(difference: Double) {
        val stamp = lock.writeLock()
        try {
            balance += difference
        } finally {
            lock.unlockWrite(stamp)
        }
    }

    fun get(): Double = balance
}