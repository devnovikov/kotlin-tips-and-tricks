package devnovikov.lincheck.problem

class BankAccountRaceCondition {
    var balance = 0.0

    fun withdraw(amount: Double): Boolean {
        if (balance >= amount) {
            balance -= amount
            return true
        }
        return false
    }

    fun deposit(amount: Double) {
        balance += amount
    }
}