package transformations

import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class ScalingTransformation(
    private val config: ScalingTransformationConfig
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val newImage = Mat()
        val newImageSize = config.outputSize
        Imgproc.resize(
            colorImage,
            newImage,
            newImageSize,
            config.scaleInXDirection,
            config.scaleInYDirection,
            config.scalingType.type
        )
        return newImage
    }
}