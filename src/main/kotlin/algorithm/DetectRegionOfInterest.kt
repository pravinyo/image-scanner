package algorithm

import contrastenhancement.ImageContrastAdjustParameters
import factory.ContrastEnhancementFactory
import factory.FilterFactory
import factory.TransformationFactory
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import utils.ImageUtils
import utils.OperationType
import utils.Point2D
import utils.Point2DUtility
import utils.Point2DUtility.toListOfPoint2D
import utils.Point2DUtility.toPoint2D
import kotlin.math.*

class DetectRegionOfInterest(
    private val filterFactory: FilterFactory,
    private val transformationFactory: TransformationFactory,
    private val contrastEnhancementFactory: ContrastEnhancementFactory,
    private val convexHull: ConvexHull
) {
    private var _boundaryPoints: List<Point2D> = emptyList()
    val boundaryPoints: List<Point2D> get() = _boundaryPoints

    private lateinit var _boundaryLines: List<LineSegment>
    val boundaryLines: List<LineSegment> get() = _boundaryLines

    fun process(source: Mat) {
        val edges = preProcessing(source)

        val lines = Mat()
        Imgproc.HoughLines(edges, lines, 1.0, Math.PI / 180, 100)

        val (verticalLines, horizontalLines) = getVerticalHorizontalLines(lines)
        val origins = findIntersection(verticalLines, horizontalLines, edges.rows(), edges.cols())

        val (boundaryPoints, boundaryLines) = getConvexHullBoundarySegments(origins)
        _boundaryPoints = Point2DUtility.orderedPoints(boundaryPoints).map { it.value }
        _boundaryLines = boundaryLines
        source.release()
        edges.release()
        lines.release()
    }

    private fun getConvexHullBoundarySegments(
        origins: List<Point2D>
    ): Pair<List<Point2D>, List<LineSegment>> {
        println("Computing convex")
        val twentyCornerBoundary = convexHull.computeBoundaryPoints(origins, 20)
        val fourCornerBoundary = convexHull.computeBoundaryPoints(twentyCornerBoundary, 4)
        return Pair(
            first = fourCornerBoundary,
            second = convexHull.boundaryLines()
        )
    }

    fun generateOutputImages(
        hullPoints: List<Point>, segments: List<LineSegment>, image: Mat
    ): Pair<Mat, Mat> {
        val transformation = transformationFactory.createInstance(
            OperationType.PerspectiveTransform(
                sourcePoints = hullPoints.toListOfPoint2D()
            )
        )
        val transformedImage = transformation.execute(image)
        ImageUtils.drawLines(image, segments)
        return Pair(first = transformedImage, second = image)
    }

    private fun preProcessing(image: Mat): Mat {
        val grayscaleFilter = filterFactory.createInstance(OperationType.GrayscaleFilter)
        val transformedGrayScale = grayscaleFilter.convert(image.clone())

        val contrastFilter = contrastEnhancementFactory.createInstance(
            OperationType.ImAdjustEnhancement(
                ImageContrastAdjustParameters(inputBound = Pair(100, 200))
            )
        )
        val original = contrastFilter.execute(transformedGrayScale)

        // remove noise
        val med = Mat()
        Imgproc.medianBlur(original, med, 15)

        val thr = Mat()
        Imgproc.adaptiveThreshold(med, thr, 255.0, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 23, 2.0)

        // thinner line
        val kSize = 5.0
        val kernel = Mat.ones(Size(kSize, kSize), CvType.CV_8U)
        Imgproc.dilate(thr, thr, kernel)

        val edges = Mat()
        Imgproc.Canny(thr, edges, 50.0, 200.0, 3)

        return edges
    }

    private fun getVerticalHorizontalLines(lines: Mat): Pair<List<LineSegment>, List<LineSegment>> {
        val verticalLines = mutableListOf<LineSegment>()
        val horizontalLines = mutableListOf<LineSegment>()

        for (i in 0 until lines.rows()) {
            val data = lines[i, 0]
            val rho = data[0]
            val radian = data[1]
            val a = cos(radian)
            val b = sin(radian)
            val x0 = a * rho
            val y0 = b * rho
            // compute end points of the line
            val pt1 = Point((x0 + 1000 * -b).roundToInt().toDouble(), (y0 + 1000 * a).roundToInt().toDouble())
            val pt2 = Point((x0 - 1000 * -b).roundToInt().toDouble(), (y0 - 1000 * a).roundToInt().toDouble())


            val theta = radian * 180 / Math.PI
            val theta90 = abs(90 - theta)
            val theta180 = abs(180 - theta)

            if (min(theta180, theta) > theta90) {
                verticalLines.add(LineSegment(pt1.toPoint2D(), pt2.toPoint2D()))
            } else {
                horizontalLines.add(LineSegment(pt1.toPoint2D(), pt2.toPoint2D()))
            }
        }

        return Pair(first = verticalLines, second = horizontalLines)
    }

    private fun findIntersection(
        lines1: List<LineSegment>,
        lines2: List<LineSegment>,
        rows: Int,
        cols: Int
    ): List<Point2D> {

        println("Finding intersections")
        if (lines1.isEmpty() || lines2.isEmpty()) return emptyList()

        val intersectionsPoints = mutableListOf<Point2D>()

        lines1.forEach { l1 ->
            lines2.forEach { l2 ->
                // Line l1(p1,p2) is represented by a1x + b1y = c1
                val a1 = l1.p2.y - l1.p1.y
                val b1 = l1.p1.x - l1.p2.x
                val c1 = a1 * l1.p1.x + b1 * l1.p1.y

                // Line l2(p1,p2) is represented by a2x + b2y = c2
                val a2 = l2.p2.y - l2.p1.y
                val b2 = l2.p1.x - l2.p2.x
                val c2 = a2 * l2.p1.x + b2 * l2.p1.y

                val determinant = a1 * b2 - a2 * b1

                if (determinant != 0.0) {

                    val x = (b2 * c1 - b1 * c2) / determinant
                    val y = (a1 * c2 - a2 * c1) / determinant

                    if (x >= 0 && y >= 0 && x < cols && y < rows) intersectionsPoints.add(Point2D(x, y))
                }
            }
        }

        println("Finding intersections done")
        return intersectionsPoints
    }
}