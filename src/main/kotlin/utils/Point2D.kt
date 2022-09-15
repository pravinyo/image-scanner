package utils

import org.opencv.core.Point
import kotlin.Any
import kotlin.Boolean
import kotlin.Comparable
import kotlin.Comparator
import kotlin.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.math.sqrt

class Point2D(val x: Double,val y: Double) : Comparable<Point2D> {

    companion object {
        /**
         * Returns true if a→b→c is a counterclockwise turn.
         *
         * @param a first point
         * @param b second point
         * @param c third point
         * @return { -1, 0, +1 } if a→b→c is a { clockwise, collinear; counterclocwise } turn.
         */
        fun ccw(a: Point2D, b: Point2D, c: Point2D): Int {
            val area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
            return if (area2 < 0) -1 else if (area2 > 0) +1 else 0
        }
    }

    /**
     * Returns the square of the Euclidean distance between this point and that point.
     *
     * @param that the other point
     * @return the square of the Euclidean distance between this point and that point
     */
    private fun distanceSquaredTo(that: Point2D): Double {
        val dx = x - that.x
        val dy = y - that.y
        return dx * dx + dy * dy
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param other the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    override fun compareTo(other: Point2D): Int {
        // same point
        if (y == other.y && x == other.x) return 0
        // this point is less than that point
        return if (y < other.y || y == other.y && x < other.x) -1 else 1
    }

    fun distanceTo(point: Point2D): Double {
        return sqrt(distanceSquaredTo(point))
    }


    fun toPoint(): Point {
        val point = Point()
        point.x = x
        point.y = y
        return point
    }

    /**
     * Compares two points by polar angle (between 0 and 2) with respect to this point.
     *
     * @return the comparator
     */
    fun polarOrder(): Comparator<Point2D?> {
        return PolarOrder()
    }


    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private inner class PolarOrder : Comparator<Point2D?> {
        override fun compare(q1: Point2D?, q2: Point2D?): Int {

            if (q1 == null || q2 == null) throw IllegalArgumentException("Point can't be null")

            val dx1 = q1.x - x
            val dy1 = q1.y - y
            val dx2 = q2.x - x
            val dy2 = q2.y - y
            return if (dy1 >= 0 && dy2 < 0) -1 // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0) +1 // q1 below; q2 above
            else if (dy1 == 0.0 && dy2 == 0.0) {            // 3-collinear and horizontal
                if (dx1 >= 0 && dx2 < 0) -1 else if (dx2 >= 0 && dx1 < 0) +1 else 0
            } else -ccw(this@Point2D, q1, q2) // both above or below

            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        }
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    /**
     * Compares this point to the specified point.
     *
     * @param other the other point
     * @return `true` if this point equals `other`;
     * `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other.javaClass != this.javaClass) return false
        val that = other as Point2D
        return x == that.x && y == that.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}