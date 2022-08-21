package transformations

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import transformations.RotationDirections.*

class RotationTransformation(
    private val rotationDirections: RotationDirections
) {
    fun execute(colorImage: Mat): Mat {
        val rotatedImage = Mat(colorImage.size(), colorImage.type())

        when (rotationDirections) {
            is FixedDirection -> {
                Core.rotate(colorImage, rotatedImage, rotationDirections.rotationType.value)
            }

            is Arbitrary -> {
                val size = colorImage.size()
                val center = rotationDirections.center ?: Point(size.width / 2, size.height / 2)
                val transformationMatrix =
                    Imgproc.getRotationMatrix2D(center, rotationDirections.angle, rotationDirections.scale)
                Imgproc.warpAffine(colorImage, rotatedImage, transformationMatrix, rotatedImage.size())
            }
        }

        return rotatedImage
    }
}