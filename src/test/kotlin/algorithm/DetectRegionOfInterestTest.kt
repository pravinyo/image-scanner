package algorithm

import factory.ContrastEnhancementFactory
import factory.FilterFactory
import factory.TransformationFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.ImageUtils
import utils.Point2DUtility.toListOfPoint


internal class DetectRegionOfInterestTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `end to end test for auto detect document`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")

        val kMean = KMean()
        val boundaryRegionHandler = BoundaryRegionHandler()
        val convexHull = ConvexHull(kMean, boundaryRegionHandler)
        val contrastEnhancementFactory = ContrastEnhancementFactory()
        val filterFactory = FilterFactory(contrastEnhancementFactory)
        val transformationFactory = TransformationFactory()

        val detectRegionOfInterest =
            DetectRegionOfInterest(filterFactory, transformationFactory, contrastEnhancementFactory, convexHull)
        detectRegionOfInterest.process(input.clone())

        val contourPoints = detectRegionOfInterest.boundaryPoints.toListOfPoint()
        println(contourPoints)
        val lineSegment = detectRegionOfInterest.boundaryLines
        val (transformedImage, originalImage) = detectRegionOfInterest
            .generateOutputImages(contourPoints, lineSegment, input)

        ImageUtils.saveImage("detectROI/transformed.jpeg", transformedImage)
        ImageUtils.saveImage("detectROI/original.jpeg", originalImage)
    }

}