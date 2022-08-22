package transformations

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.utils.Converters
import kotlin.math.max
import kotlin.math.sqrt

class PerspectiveTransformation(
    private val sourcePoints: List<Point>,
    private val destPoints: List<Point>? = null
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val size = if (destPoints == null) findOutputSize(sourcePoints) else findOutputSize(destPoints)
        val destinationPoints: List<Point> = destPoints ?: calculateDestinationPoints(size)

        val sourceMatrix = Converters.vector_Point2f_to_Mat(sourcePoints)
        val destMatrix = Converters.vector_Point2f_to_Mat(destinationPoints)

        val transformationMatrix = Imgproc.getPerspectiveTransform(sourceMatrix, destMatrix)
        val output = Mat(size, colorImage.type())
        Imgproc.warpPerspective(colorImage, output, transformationMatrix, size)

        return output
    }

    private fun calculateDestinationPoints(size: Size): List<Point> {
        val destinationPoints = mutableListOf<Point>()
        destinationPoints.add(Point(0.0, 0.0))
        destinationPoints.add(Point(size.width, 0.0))
        destinationPoints.add(Point(0.0, size.height))
        destinationPoints.add(Point(size.width, size.height))
        return destinationPoints
    }

    private fun findOutputSize(points: List<Point>): Size {
        val topLeft = points[0]
        val topRight = points[1]
        val bottomLeft = points[2]
        val bottomRight = points[3]

        val bottomWidth = bottomRight.distanceTo(bottomLeft)
        val topWidth = topRight.distanceTo(topLeft)
        val maxWidth = max(bottomWidth, topWidth)

        val rightHeight = topRight.distanceTo(bottomRight)
        val leftHeight = topLeft.distanceTo(bottomLeft)
        val maxHeight = max(rightHeight, leftHeight)

        return Size(maxWidth, maxHeight)
    }

    private fun Point.distanceTo(other: Point): Double {
        return sqrt(distanceSquaredTo(other))
    }

    private fun Point.distanceSquaredTo(other: Point): Double {
        val dx = this.x - other.x
        val dy = this.y - other.y
        return dx * dx + dy * dy
    }
}