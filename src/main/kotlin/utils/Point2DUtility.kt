package utils

import org.opencv.core.Point
import kotlin.math.sqrt

object Point2DUtility {
    fun orderedPoints(points: List<Point2D>): Map<Int, Point2D> {
        var xc = 0.0
        var yc = 0.0
        val size = points.size

        points.forEach { point ->
            xc += point.x / size
            yc += point.y / size
        }

        val centerPoint = Point2D(xc, yc)
        val map = mutableMapOf<Int, Point2D>()
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

    fun Point.distanceTo(other: Point): Double {
        return sqrt(distanceSquaredTo(other))
    }

    private fun Point.distanceSquaredTo(other: Point): Double {
        val dx = this.x - other.x
        val dy = this.y - other.y
        return dx * dx + dy * dy
    }

    fun Point.toPoint2D(): Point2D {
        return Point2D(this.x, this.y)
    }

    fun List<Point2D>.toListOfPoint(): List<Point> {
        return this.map {
            it.toPoint()
        }
    }

    fun List<Point>.toListOfPoint2D(): List<Point2D> {
        return this.map {
            it.toPoint2D()
        }
    }
}