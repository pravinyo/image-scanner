import java.util.*

class BackupManager {

    private val snapshotHistory = Stack<Snapshot>()

    val size: Int
        get() = snapshotHistory.size

    fun add(snapshot: Snapshot) {
        snapshotHistory.push(snapshot)
    }
}