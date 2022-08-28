package transformations

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import transformations.RotationTransformParameters.*

class RotationTransformation(
    private val parameters: RotationTransformParameters
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val rotatedImage = Mat(colorImage.size(), colorImage.type())

        when (parameters) {
            is FixedDirection -> {
                Core.rotate(colorImage, rotatedImage, parameters.rotationType.value)
            }

            is ArbitraryDirection -> {
                val size = colorImage.size()
                val center = parameters.center ?: Point(size.width / 2, size.height / 2)
                val transformationMatrix =
                    Imgproc.getRotationMatrix2D(
                        center,
                        parameters.angle,
                        parameters.scale
                    )
                Imgproc.warpAffine(colorImage, rotatedImage, transformationMatrix, rotatedImage.size())
            }
        }

        return rotatedImage
    }
}

enum class FixedRotationDirection(val value: Int) {
    DIRECTION_CLOCKWISE_90(Core.ROTATE_90_CLOCKWISE),
    DIRECTION_180(Core.ROTATE_180),
    DIRECTION_CLOCKWISE_270(Core.ROTATE_90_COUNTERCLOCKWISE)
}

sealed class RotationTransformParameters {
    data class FixedDirection(val rotationType: FixedRotationDirection) : RotationTransformParameters()
    data class ArbitraryDirection(val angle: Double, val scale: Double = 1.0, val center: Point? = null) : RotationTransformParameters()
}