import org.opencv.core.Mat

class StateManager {
    private lateinit var activeImage : Mat

    fun initialize(image: Mat) {
        activeImage = image.clone()
    }

    fun setActiveImage(image: Mat) {
        activeImage = image
    }

    fun getActiveImage(): Mat {
        return activeImage.clone()
    }


}