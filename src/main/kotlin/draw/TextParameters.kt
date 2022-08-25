package draw

import org.opencv.core.Core
import org.opencv.core.Point
import org.opencv.core.Scalar

data class TextParameters(
    val text: String,
    val bottomLeftCorner: Point,
    val fontFace: Int = Core.FONT_HERSHEY_COMPLEX,
    val fontScale: Double = 1.0,
    val color: Scalar = Scalar.all(255.0),
    val thickness: Int = 1,
    val bottomLeftOrigin: Boolean = false,
    val lineType: Int = Core.LINE_8,
    val baseLine: Int = 0
)