import org.opencv.core.Mat

class ImageEditor(
    val image : Mat
) {
    fun activeImage(): Mat {
        return image.clone()
    }
}