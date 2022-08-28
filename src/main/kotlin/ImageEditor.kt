import org.opencv.core.Mat

class ImageEditor(
    val image: Mat,
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

    fun resetOperationList(operationList: List<String>) {
        stateManager.resetOperationsInfo(operationList)
    }

    fun createSnapshot(): Snapshot {
        return Snapshot(stateManager.getOperationsInfo(), stateManager.getActiveImage(), this)
    }

    fun takeCommand() {
        backupManager.add(createSnapshot())
    }
}