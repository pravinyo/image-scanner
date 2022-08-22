package transformations

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import transformations.RotationTransformationConfig.*

class RotationTransformation(
    private val rotationTransformationConfig: RotationTransformationConfig
) {
    fun execute(colorImage: Mat): Mat {
        val rotatedImage = Mat(colorImage.size(), colorImage.type())

        when (rotationTransformationConfig) {
            is FixedDirectionConfig -> {
                Core.rotate(colorImage, rotatedImage, rotationTransformationConfig.rotationType.value)
            }

            is ArbitraryDirectionConfig -> {
                val size = colorImage.size()
                val center = rotationTransformationConfig.center ?: Point(size.width / 2, size.height / 2)
                val transformationMatrix =
                    Imgproc.getRotationMatrix2D(
                        center,
                        rotationTransformationConfig.angle,
                        rotationTransformationConfig.scale
                    )
                Imgproc.warpAffine(colorImage, rotatedImage, transformationMatrix, rotatedImage.size())
            }
        }

        return rotatedImage
    }
}