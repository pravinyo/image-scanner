package algorithm

import utils.Point2D

class KMean {
    private lateinit var points: MutableList<Point2D>
    private lateinit var clusters: Array<Cluster>
    val centroids: Array<Point2D>
        get() {
            val centroids = Array(clusters.size) { Point2D(0.0, 0.0) }
            for (i in clusters.indices) {
                centroids[i] = clusters[i].centroid
            }
            return centroids
        }

    fun setup(points: List<Point2D>, clusterCount: Int) {
        this.points = points.toMutableList()
        clusters = initializeClusters(clusterCount)
    }

    private fun initializeClusters(clusterCount: Int): Array<Cluster> {
        val clusters = Array(clusterCount) { Cluster(Point2D(0.0, 0.0)) }
        points.shuffle()

        // start with first random data point
        clusters[0] = Cluster(points[0])

        // compute remaining clusterCount-1 clusters
        for (clusterIndex in 1 until clusterCount) {

            // Initialize distance array for nearest centroid
            val distance = DoubleArray(points.size)
            for (pointIndex in points.indices) {
                val point = points[pointIndex]
                var d = Double.MAX_VALUE

                // compute distance of 'point' from each of the previously
                // selected centroid and store the minimum distance
                for (cluster in clusters) {
                    val tempDistance = cluster.centroid.distanceTo(point)
                    d = d.coerceAtMost(tempDistance)
                }
                distance[pointIndex] = d
            }

            // select data point with maximum distance as our next centroid
            var maxDistancePointIndex = 0
            var maxDistance = distance[0]
            for (i in 1 until distance.size) {
                if (maxDistance < distance[i]) {
                    maxDistance = distance[i]
                    maxDistancePointIndex = i
                }
            }
            clusters[clusterIndex] = Cluster(points[maxDistancePointIndex])
        }
        return clusters
    }
}

data class Cluster(val centroid: Point2D)