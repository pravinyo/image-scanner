package algorithm

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.Point2D

class BoundaryRegionHandlerTest {

    @Test
    fun `should find the boundary points for points`() {
        val boundaryRegionHandler = BoundaryRegionHandler()
        val points = mutableListOf(
            Point2D(0.0, 1.0),
            Point2D(1.0, 1.0),
            Point2D(1.0, 0.0),
            Point2D(0.0, 0.0)
        )
        points.shuffle()

        boundaryRegionHandler.process(points.toTypedArray())

        val actualBoundaryPoints = boundaryRegionHandler.boundaryPoints()
        val expectedBoundaryPoints = mutableListOf(
            Point2D(0.0, 0.0),
            Point2D(1.0, 0.0),
            Point2D(1.0, 1.0),
            Point2D(0.0, 1.0),
        )
        assertEquals(expectedBoundaryPoints, actualBoundaryPoints)
    }

    @Test
    fun `should find the boundary lines for points`() {
        val boundaryRegionHandler = BoundaryRegionHandler()
        val points = mutableListOf(
            Point2D(0.0, 1.0),
            Point2D(1.0, 1.0),
            Point2D(1.0, 0.0),
            Point2D(0.0, 0.0)
        )
        points.shuffle()

        boundaryRegionHandler.process(points.toTypedArray())

        val actualBoundaryLines = boundaryRegionHandler.boundaryLineSegments()
        val expectedBoundaryLines = mutableListOf(
            LineSegment(Point2D(0.0, 0.0), Point2D(0.0, 1.0)),
            LineSegment(Point2D(0.0, 0.0), Point2D(1.0, 0.0)),
            LineSegment(Point2D(1.0, 0.0), Point2D(1.0, 1.0)),
            LineSegment(Point2D(1.0, 1.0), Point2D(0.0, 1.0))
        )
        assertEquals(expectedBoundaryLines, actualBoundaryLines)
    }
}