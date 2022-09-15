package algorithm

import utils.Point2D

class ConvexHull(
    private val kMean: KMean,
    private val boundaryRegionHandler: BoundaryRegionHandler
) {
    fun computeBoundaryPoints(origins: List<Point2D>, clusterCount: Int): List<Point2D> {
        kMean.setup(origins, clusterCount)
        val newOrigins = kMean.centroids
        boundaryRegionHandler.process(newOrigins)
        return boundaryRegionHandler.boundaryPoints().distinct()
    }

    fun boundaryLines() = boundaryRegionHandler.boundaryLineSegments()
}