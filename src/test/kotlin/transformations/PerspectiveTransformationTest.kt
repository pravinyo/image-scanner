package transformations

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import utility.ImageUtils
import utils.Point2D

class PerspectiveTransformationTest {
    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given 4 points for input and output image, it should transform inside part to separate image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val sourcePoints = mutableListOf<Point2D>()
        sourcePoints.add(Point2D(190.0, 300.0))
        sourcePoints.add(Point2D(1100.0, 290.0))
        sourcePoints.add(Point2D(198.0, 957.0))
        sourcePoints.add(Point2D(1140.0, 957.0))

        val destinationPoints = mutableListOf<Point2D>()
        destinationPoints.add(Point2D(0.0, 0.0))
        destinationPoints.add(Point2D(300.0, 0.0))
        destinationPoints.add(Point2D(0.0, 300.0))
        destinationPoints.add(Point2D(300.0, 300.0))

        sourcePoints.forEach{
            Imgproc.circle(input, it.toPoint(), 4, Scalar(0.0, 0.0, 255.0), 4)
        }
        ImageUtils.saveImage("transformation/perspective_1.jpg", input)
        val perspectiveTransformation = PerspectiveTransformation(sourcePoints, destinationPoints)

        val actual = perspectiveTransformation.execute(input)

        assertEquals(Size(300.0, 300.0), actual.size())
        assertEquals(input.type(), actual.type())
        assertEquals(input.channels(), actual.channels())
        ImageUtils.saveImage("transformation/perspective_2.jpg", actual)
    }

    @Test
    fun `given 4 points for input, it should transform inside part to separate image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val sourcePoints = mutableListOf<Point2D>()
        sourcePoints.add(Point2D(190.0, 300.0))
        sourcePoints.add(Point2D(1100.0, 290.0))
        sourcePoints.add(Point2D(198.0, 957.0))
        sourcePoints.add(Point2D(1140.0, 957.0))
        val perspectiveTransformation = PerspectiveTransformation(sourcePoints)

        val actual = perspectiveTransformation.execute(input)

        assertEquals(Size(942.0, 668.0), actual.size())
        assertEquals(input.type(), actual.type())
        assertEquals(input.channels(), actual.channels())
    }
}