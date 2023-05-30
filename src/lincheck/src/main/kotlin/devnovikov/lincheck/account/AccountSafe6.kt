package devnovikov.lincheck.account

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AccountSafe6 {
    private var balance = 0.0
    private val mutex = Mutex()

    suspend fun modify(difference: Double) {
        mutex.withLock {
            balance += difference
        }
    }

    fun get(): Double = balance
}