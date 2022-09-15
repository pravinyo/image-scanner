package algorithm

import utils.Point2D
import java.util.*

class BoundaryRegionHandler {
    private val hull = Stack<Point2D>()

    fun process(points: Array<Point2D>) {
        require(points.isNotEmpty()) { "array is of length 0" }
        hull.clear()
        startProcessing(points)
        assert(isConvex)
    }

    fun boundaryPoints(): Iterable<Point2D> {
        val s = Stack<Point2D>()
        for (p in hull) s.push(p)
        return s
    }

    fun boundaryLineSegments(): List<LineSegment> {
        val lineSegments = ArrayList<LineSegment>()
        var prevPoint: Point2D? = null
        for ((index, point) in hull.withIndex()) {
            if (index != 0) {
                lineSegments.add(LineSegment(prevPoint!!, point))
            }
            prevPoint = point
            if (index == 0) {
                lineSegments.add(LineSegment(prevPoint, prevPoint))
            }
        }
        val (startPoint) = lineSegments[0]
        lineSegments.removeAt(0)
        lineSegments.add(0, LineSegment(startPoint, prevPoint!!))
        return lineSegments
    }

    // check that boundary of hull is strictly convex
    private val isConvex: Boolean
        get() {
            val n = hull.size
            if (n <= 2) return true
            val points = arrayOfNulls<Point2D>(n)
            var k = 0
            for (p in boundaryPoints()) {
                points[k++] = p
            }
            for (i in 0 until n) {
                if (Point2D.ccw(points[i]!!, points[(i + 1) % n]!!, points[(i + 2) % n]!!) <= 0) {
                    return false
                }
            }
            return true
        }

    private fun startProcessing(points: Array<Point2D>) {
        // defensive copy
        val n = points.size
        val a = arrayOfNulls<Point2D>(n)
        for (i in 0 until n) {
            a[i] = points[i]
        }

        // preprocess so that a[0] has lowest y-coordinate; break ties by x-coordinate
        // a[0] is an extreme point of the convex hull
        // (alternatively, could do easily in linear time)
        Arrays.sort(a)
        // sort by polar angle with respect to base point a[0],
        // breaking ties by distance to a[0]
        Arrays.sort(a, 1, n, a[0]!!.polarOrder())

        hull.push(a[0]) // a[0] is first extreme point


        // find index k1 of first point not equal to a[0]
        var k1 = 1
        while (k1 < n) {
            if (a[0]!! != a[k1]) break
            k1++
        }
        if (k1 == n) return  // all points equal
        var k2 = k1 + 1
        while (k2 < n) {
            if (Point2D.ccw(a[0]!!, a[k1]!!, a[k2]!!) !== 0) {
                break
            }
            k2++
        }
        hull.push(a[k2 - 1]) // a[k2-1] is second extreme point

        // Graham scan; note that a[n-1] is extreme point different from a[0]
        for (i in k2 until n) {
            var top = hull.pop()
            while (Point2D.ccw(hull.peek()!!, top!!, a[i]!!) <= 0) {
                top = hull.pop()
            }
            hull.push(top)
            hull.push(a[i])
        }
    }
}