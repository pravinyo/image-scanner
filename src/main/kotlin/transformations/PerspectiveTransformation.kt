package transformations

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.utils.Converters
import java.util.*
import kotlin.math.max
import kotlin.math.sqrt

class PerspectiveTransformation(
    private val sourcePoints: List<Point>,
    private val destPoints: List<Point>? = null
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val srcPoints = orderedPoints(sourcePoints).let {
            listOf(
                it[0] ?: error("Point 1 missing"),
                it[1] ?: error("Point 2 missing"),
                it[2] ?: error("Point 3 missing"),
                it[3] ?: error("Point 4 missing")
            )
        }

        val size = if (destPoints == null) findOutputSize(srcPoints) else findOutputSize(destPoints)
        val destinationPoints: List<Point> = destPoints ?: calculateDestinationPoints(size)

        val sourceMatrix = Converters.vector_Point2f_to_Mat(srcPoints)
        val destMatrix = Converters.vector_Point2f_to_Mat(destinationPoints)

        val transformationMatrix = Imgproc.getPerspectiveTransform(sourceMatrix, destMatrix)
        val output = Mat(size, colorImage.type())
        Imgproc.warpPerspective(colorImage, output, transformationMatrix, size)

        return output
    }

    private fun orderedPoints(points: List<Point>): Map<Int, Point> {
        var xc = 0.0
        var yc = 0.0
        val size = points.size

        points.forEach { point ->
            xc += point.x / size
            yc += point.y / size
        }

        val centerPoint = Point(xc, yc)
        val map = mutableMapOf<Int, Point>()
        var isPointValid = true

        points.forEach { point ->
            var index = -1
            if (point.x < centerPoint.x && point.y < centerPoint.y) {
                index = 0
            } else if (point.x > centerPoint.x && point.y < centerPoint.y) {
                index = 1
            } else if (point.x < centerPoint.x && point.y > centerPoint.y) {
                index = 2
            } else if (point.x > centerPoint.x && point.y > centerPoint.y) {
                index = 3
            }

            if (map.containsKey(index)) {
                isPointValid = false
                return@forEach
            }

            map[index] = point
        }

        return if (isPointValid) map
        else {
            map.clear()
            points.toMutableList().sortWith(Comparator sort@{ o1, o2 ->

                if (o1.y == o2.y && o1.x == o2.x) return@sort 0
                // this point is less than that point
                if (o1.y < o2.y || o1.y == o2.y && o1.x < o2.x) return@sort -1
                // this point is greater
                else return@sort 1
            })
            for ((index, p) in points.withIndex()) {
                println("Point:$p at index:$index")
                map[index] = p
            }
            map
        }
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