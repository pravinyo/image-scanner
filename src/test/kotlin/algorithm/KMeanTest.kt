package algorithm

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.Point2D
import kotlin.random.Random

class KMeanTest {

    @Test
    fun `should compute centroid of the points`() {
        val kMean = KMean()
        val points = mutableListOf<Point2D>()

        for (i in 0..99) {
            val x: Double = 100 * Random.nextDouble() * if (Random.nextBoolean()) 1 else -1
            val y: Double = 100 * Random.nextDouble() * if (Random.nextBoolean()) 1 else -1
            points.add(Point2D(x, y))
        }

        kMean.setup(points, 4)
        val centroids = kMean.centroids

        assertEquals(4, centroids.size)
    }
}