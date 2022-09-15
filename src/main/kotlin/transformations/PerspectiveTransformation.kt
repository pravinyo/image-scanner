package transformations

import utils.Point2D
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.utils.Converters
import utils.Point2DUtility
import utils.Point2DUtility.toListOfPoint
import kotlin.math.max

class PerspectiveTransformation(
    private val sourcePoints: List<Point2D>,
    private val destPoints: List<Point2D>? = null
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val srcPoints = Point2DUtility.orderedPoints(sourcePoints).let {
            listOf(
                it[0] ?: error("Point 1 missing"),
                it[1] ?: error("Point 2 missing"),
                it[2] ?: error("Point 3 missing"),
                it[3] ?: error("Point 4 missing")
            )
        }

        val size = if (destPoints == null) findOutputSize(srcPoints) else findOutputSize(destPoints)
        val destinationPoints: List<Point2D> = destPoints ?: calculateDestinationPoints(size)

        val sourceMatrix = Converters.vector_Point2f_to_Mat(srcPoints.toListOfPoint())
        val destMatrix = Converters.vector_Point2f_to_Mat(destinationPoints.toListOfPoint())

        val transformationMatrix = Imgproc.getPerspectiveTransform(sourceMatrix, destMatrix)
        val output = Mat(size, colorImage.type())
        Imgproc.warpPerspective(colorImage, output, transformationMatrix, size)

        return output
    }

    private fun calculateDestinationPoints(size: Size): List<Point2D> {
        val destinationPoints = mutableListOf<Point2D>()
        destinationPoints.add(Point2D(0.0, 0.0))
        destinationPoints.add(Point2D(size.width, 0.0))
        destinationPoints.add(Point2D(0.0, size.height))
        destinationPoints.add(Point2D(size.width, size.height))
        return destinationPoints
    }

    private fun findOutputSize(points: List<Point2D>): Size {
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
}