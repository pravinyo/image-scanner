package transformations

import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ScalingTransformation(
    private val parameters: ScalingTransformParameters
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val newImage = Mat()
        val newImageSize = parameters.outputSize
        Imgproc.resize(
            colorImage,
            newImage,
            newImageSize,
            parameters.scaleInXDirection,
            parameters.scaleInYDirection,
            parameters.scalingType.type
        )
        return newImage
    }
}

const val DEFAULT_SCALING = 0.0

enum class ScalingType(val type: Int) {
    SCALE_UP(Imgproc.INTER_CUBIC),
    SCALE_DOWN(Imgproc.INTER_AREA),
    DEFAULT(Imgproc.INTER_LINEAR)
}

data class ScalingTransformParameters(
    val outputSize: Size = Size(),
    val scaleInXDirection: Double = DEFAULT_SCALING,
    val scaleInYDirection: Double = DEFAULT_SCALING,
    val scalingType: ScalingType = ScalingType.DEFAULT
)