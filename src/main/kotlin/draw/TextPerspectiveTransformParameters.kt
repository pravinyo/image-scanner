package draw

import org.opencv.core.Point

data class TextPerspectiveTransformParameters(
    val topLeft: Point = Point(0.0, 0.0),
    val topRight: Point = Point(0.0, 40.0),
    val bottomLeft: Point = Point(0.0, 0.0),
    val bottomRight: Point = Point(0.0, 0.0)
)
