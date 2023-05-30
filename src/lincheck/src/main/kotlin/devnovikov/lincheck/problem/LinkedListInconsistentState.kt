package devnovikov.lincheck.problem

class LinkedListInconsistentState {
    private val head = Node(0) // dummy node
    private var size = 0

    fun add(value: Int) {
        val newNode = Node(value)
        newNode.next = head.next
        head.next = newNode
        size++
    }

    fun remove(value: Int): Boolean {
        var curNode = head
        while (curNode.next != null) {
            if (curNode.next!!.value == value) {
                curNode.next = curNode.next!!.next
                size--
                return true
            }
            curNode = curNode.next!!
        }
        return false
    }

    fun getSize() = size

    private class Node(val value: Int, var next: Node? = null)
}