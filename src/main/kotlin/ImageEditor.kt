import org.opencv.core.Mat

class ImageEditor(
    val image : Mat,
    private val stateManager: StateManager
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


}