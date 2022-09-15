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
        val input = ImageUtils.loadImage("input/notebook.jpeg")

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
        println("contourPoints : $contourPoints")
        val lineSegment = detectRegionOfInterest.boundaryLines
        println("lineSegment : $lineSegment")
        val (transformedImage, originalImage) = detectRegionOfInterest
            .generateOutputImages(contourPoints, lineSegment, input)

        ImageUtils.saveImage("detectROI/transformed.jpeg", transformedImage)
        ImageUtils.saveImage("detectROI/original.jpeg", originalImage)
    }

//    @Test
//    fun `understanding algorithm steps`() {
//        val image = ImageUtils.loadImage("input/notebook.jpeg")
//
//        val contrastEnhancementFactory = ContrastEnhancementFactory()
//        val filterFactory = FilterFactory(contrastEnhancementFactory)
//
//        val grayscaleFilter = filterFactory.createInstance(OperationType.GrayscaleFilter)
//        val transformedGrayScale = grayscaleFilter.convert(image.clone())
//        ImageUtils.saveImage("detectROI/1.jpeg", transformedGrayScale)
//
//        val contrastFilter = contrastEnhancementFactory.createInstance(
//            OperationType.ImAdjustEnhancement(
//                ImageContrastAdjustParameters(inputBound = Pair(100, 200))
//            )
//        )
//        val original = contrastFilter.execute(transformedGrayScale)
//        ImageUtils.saveImage("detectROI/2.jpeg", original)
//
//        // remove noise
//        val med = Mat()
//        Imgproc.medianBlur(original, med, 15)
//        ImageUtils.saveImage("detectROI/3.jpeg", med)
//
//        val thr = Mat()
//        Imgproc.adaptiveThreshold(med, thr, 255.0, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 23, 2.0)
//        ImageUtils.saveImage("detectROI/4.jpeg", thr)
//
//        // thinner line
//        val kSize = 5.0
//        val kernel = Mat.ones(Size(kSize, kSize), CvType.CV_8U)
//        Imgproc.dilate(thr, thr, kernel)
//        ImageUtils.saveImage("detectROI/5.jpeg", thr)
//
//        val edges = Mat()
//        Imgproc.Canny(thr, edges, 50.0, 200.0, 3)
//        ImageUtils.saveImage("detectROI/6.jpeg", edges)
//
//        val lines = Mat()
//        Imgproc.HoughLines(edges, lines, 1.0, Math.PI / 180, 100)
//
//        val (verticalLines, horizontalLines) = getVerticalHorizontalLines(lines)
//        val image2 = image.clone()
//        drawLines(image2, verticalLines)
//        ImageUtils.saveImage("detectROI/7.jpeg", image2)
//
//        val image3 = image.clone()
//        drawLines(image3, horizontalLines)
//        ImageUtils.saveImage("detectROI/8.jpeg", image3)
//
//        val image4 = image.clone()
//        drawLines(image4, verticalLines)
//        drawLines(image4, horizontalLines)
//        ImageUtils.saveImage("detectROI/9.jpeg", image4)
//
//        val image5 = image.clone()
//        val origins = findIntersection(verticalLines, horizontalLines, edges.rows(), edges.cols())
//        origins.forEach{
//            Imgproc.circle(image5, it.toPoint(), 4, Scalar(0.0, 0.0, 255.0), 4)
//        }
//        ImageUtils.saveImage("detectROI/10.jpeg", image5)
//
//        val kMean = KMean()
//        val boundaryRegionHandler = BoundaryRegionHandler()
//        val convexHull = ConvexHull(kMean, boundaryRegionHandler)
//
//        val (boundaryPoints, boundaryLines) = getConvexHullBoundarySegments(origins, convexHull)
//        val image6 = image.clone()
//        boundaryPoints.forEach{
//            Imgproc.circle(image6, it.toPoint(), 4, Scalar(0.0, 0.0, 255.0), 4)
//        }
//        ImageUtils.saveImage("detectROI/11.jpeg", image6)
//
//        val _boundaryPoints = Point2DUtility.orderedPoints(boundaryPoints).map { it.value }
//        val image7 = image.clone()
//        _boundaryPoints.forEach{
//            Imgproc.circle(image7, it.toPoint(), 4, Scalar(0.0, 0.0, 255.0), 4)
//        }
//        ImageUtils.saveImage("detectROI/12.jpeg", image7)
//
//        val _boundaryLines = boundaryLines
//
//        println(_boundaryLines)
//        print(_boundaryPoints)
//    }
//
//    private fun getVerticalHorizontalLines(lines: Mat): Pair<List<LineSegment>, List<LineSegment>> {
//        val verticalLines = mutableListOf<LineSegment>()
//        val horizontalLines = mutableListOf<LineSegment>()
//
//        for (i in 0 until lines.rows()) {
//            val data = lines[i, 0]
//            val rho = data[0]
//            val radian = data[1]
//            val a = cos(radian)
//            val b = sin(radian)
//            val x0 = a * rho
//            val y0 = b * rho
//            // compute end points of the line
//            val pt1 = Point((x0 + 1000 * -b).roundToInt().toDouble(), (y0 + 1000 * a).roundToInt().toDouble())
//            val pt2 = Point((x0 - 1000 * -b).roundToInt().toDouble(), (y0 - 1000 * a).roundToInt().toDouble())
//
//
//            val theta = radian * 180 / Math.PI
//            val theta90 = abs(90 - theta)
//            val theta180 = abs(180 - theta)
//
//            if (min(theta180, theta) > theta90) {
//                verticalLines.add(LineSegment(pt1.toPoint2D(), pt2.toPoint2D()))
//            } else {
//                horizontalLines.add(LineSegment(pt1.toPoint2D(), pt2.toPoint2D()))
//            }
//        }
//
//        return Pair(first = verticalLines, second = horizontalLines)
//    }
//
//    private fun findIntersection(
//        lines1: List<LineSegment>,
//        lines2: List<LineSegment>,
//        rows: Int,
//        cols: Int
//    ): List<Point2D> {
//
//        println("Finding intersections")
//        if (lines1.isEmpty() || lines2.isEmpty()) return emptyList()
//
//        val intersectionsPoints = mutableListOf<Point2D>()
//
//        lines1.forEach { l1 ->
//            lines2.forEach { l2 ->
//                // Line l1(p1,p2) is represented by a1x + b1y = c1
//                val a1 = l1.p2.y - l1.p1.y
//                val b1 = l1.p1.x - l1.p2.x
//                val c1 = a1 * l1.p1.x + b1 * l1.p1.y
//
//                // Line l2(p1,p2) is represented by a2x + b2y = c2
//                val a2 = l2.p2.y - l2.p1.y
//                val b2 = l2.p1.x - l2.p2.x
//                val c2 = a2 * l2.p1.x + b2 * l2.p1.y
//
//                val determinant = a1 * b2 - a2 * b1
//
//                if (determinant != 0.0) {
//
//                    val x = (b2 * c1 - b1 * c2) / determinant
//                    val y = (a1 * c2 - a2 * c1) / determinant
//
//                    if (x >= 0 && y >= 0 && x < cols && y < rows) intersectionsPoints.add(Point2D(x, y))
//                }
//            }
//        }
//
//        println("Finding intersections done")
//        return intersectionsPoints
//    }
//
//    private fun getConvexHullBoundarySegments(
//        origins: List<Point2D>,
//        convexHull: ConvexHull
//    ): Pair<List<Point2D>, List<LineSegment>> {
//        println("Computing convex")
//        val twentyCornerBoundary = convexHull.computeBoundaryPoints(origins, 20)
//        val fourCornerBoundary = convexHull.computeBoundaryPoints(twentyCornerBoundary, 4)
//        return Pair(
//            first = fourCornerBoundary,
//            second = convexHull.boundaryLines()
//        )
//    }
}