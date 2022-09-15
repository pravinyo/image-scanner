package algorithm

import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.Point2D

class ConvexHullTest {

    @Test
    fun `should call k-mean to find centroid`() {
        val kMean = mockk<KMean>()
        val boundaryRegionHandler = mockk<BoundaryRegionHandler>()
        val convexHull = ConvexHull(kMean, boundaryRegionHandler)

        val centroid = Array(4) { Point2D(0.0, 0.0) }
        val boundaryPoints = mutableListOf(
            Point2D(0.0, 1.0),
            Point2D(1.0, 1.0),
            Point2D(1.0, 0.0),
            Point2D(0.0, 0.0))

        justRun { kMean.setup(any(), any()) }
        every { kMean.centroids } returns centroid
        justRun { boundaryRegionHandler.process(any()) }
        every { boundaryRegionHandler.boundaryPoints() } returns boundaryPoints

        convexHull.computeBoundaryPoints(emptyList(), 4)

        verify(exactly = 1) {
            kMean.setup(emptyList(), 4)
        }
    }

    @Test
    fun `should call boundary region handler`() {
        val kMean = mockk<KMean>()
        val boundaryRegionHandler = mockk<BoundaryRegionHandler>()
        val convexHull = ConvexHull(kMean, boundaryRegionHandler)

        val centroid = Array(4) { Point2D(0.0, 0.0) }
        val boundaryPoints = mutableListOf(
            Point2D(0.0, 1.0),
            Point2D(1.0, 1.0),
            Point2D(1.0, 0.0),
            Point2D(0.0, 0.0))

        justRun { kMean.setup(any(), any()) }
        every { kMean.centroids } returns centroid
        justRun { boundaryRegionHandler.process(any()) }
        every { boundaryRegionHandler.boundaryPoints() } returns boundaryPoints

        val actualBoundaryPoints = convexHull.computeBoundaryPoints(emptyList(), 4)

        assertEquals(boundaryPoints, actualBoundaryPoints)
        verifySequence {
            boundaryRegionHandler.process(centroid)
            boundaryRegionHandler.boundaryPoints()
        }
    }

    @Test
    fun `should call boundary handler for boundary lines`() {
        val kMean = mockk<KMean>()
        val boundaryRegionHandler = mockk<BoundaryRegionHandler>()
        val convexHull = ConvexHull(kMean, boundaryRegionHandler)
        every { boundaryRegionHandler.boundaryLineSegments() } returns emptyList()

        convexHull.boundaryLines()

        verify(exactly = 1) {
            boundaryRegionHandler.boundaryLineSegments()
        }
    }
}