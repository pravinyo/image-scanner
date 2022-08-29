package editor

import java.util.*

class BackupManager {

    private val snapshotHistory = Stack<Snapshot>()

    val size: Int
        get() = snapshotHistory.size

    fun add(snapshot: Snapshot) {
        snapshotHistory.push(snapshot)
    }

    fun runLastSnapshot(): Boolean {
        if (snapshotHistory.isNotEmpty()) {
            val snapshot = snapshotHistory.pop()
            println("Operations: ${snapshot.getOperations()}")
            snapshot.restore()
            return true
        }
        return false
    }
}