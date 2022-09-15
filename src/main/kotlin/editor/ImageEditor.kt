package editor

import utils.OperationType
import commands.Command
import org.opencv.core.Mat

class ImageEditor(
    image: Mat,
    private val stateManager: StateManager,
    private val backupManager: BackupManager
) {

    init {
        stateManager.initialize(image)
    }

    fun getActiveImage(): Mat {
        return stateManager.getActiveImage()
    }

    fun setActiveImage(newActiveImage: Mat) {
        stateManager.setActiveImage(newActiveImage)
    }

    fun resetOperationList(operationList: List<OperationType>) {
        stateManager.resetOperationsInfo(operationList)
    }

    fun createSnapshot(): Snapshot {
        return Snapshot(stateManager.getOperationsInfo(), stateManager.getActiveImage(), this)
    }

    fun takeCommand(command: Command) {
        stateManager.appendOperationInfo(command.operationType())
        backupManager.add(createSnapshot())
        command.execute()
    }

    fun undoChanges() {
        backupManager.runLastSnapshot()
    }
}