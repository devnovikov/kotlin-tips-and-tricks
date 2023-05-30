package devnovikov.lincheck.account

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.write

class AccountSafe4 {
    private var balance = 0.0
    private val lock = ReentrantReadWriteLock()

    fun modify(difference: Double) {
        lock.write {
            balance += difference
        }
    }

    fun get(): Double = balance
}