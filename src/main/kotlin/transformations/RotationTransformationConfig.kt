package transformations

import org.opencv.core.Core
import org.opencv.core.Point

enum class FixedRotationDirection(val value: Int) {
    DIRECTION_CLOCKWISE_90(Core.ROTATE_90_CLOCKWISE),
    DIRECTION_180(Core.ROTATE_180),
    DIRECTION_CLOCKWISE_270(Core.ROTATE_90_COUNTERCLOCKWISE)
}

sealed class RotationTransformationConfig {
    data class FixedDirectionConfig(val rotationType: FixedRotationDirection) : RotationTransformationConfig()
    data class ArbitraryDirectionConfig(val angle: Double, val scale: Double = 1.0, val center: Point? = null) : RotationTransformationConfig()
}