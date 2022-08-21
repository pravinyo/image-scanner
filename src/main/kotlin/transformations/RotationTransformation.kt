package transformations

import org.opencv.core.Core
import org.opencv.core.Mat

class RotationTransformation {
    fun execute(colorImage: Mat): Mat {
        val rotatedImage = Mat()
        Core.rotate(colorImage, rotatedImage, Core.ROTATE_90_CLOCKWISE)
        return rotatedImage
    }
}