package transformations

import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

const val DEFAULT_SCALING = 0.0

enum class ScalingType(val type: Int) {
    SCALE_UP(Imgproc.INTER_CUBIC),
    SCALE_DOWN(Imgproc.INTER_AREA),
    DEFAULT(Imgproc.INTER_LINEAR)
}

data class ScalingTransformationConfig(
    val outputSize: Size = Size(),
    val scaleInXDirection: Double = DEFAULT_SCALING,
    val scaleInYDirection: Double = DEFAULT_SCALING,
    val scalingType: ScalingType = ScalingType.DEFAULT
)