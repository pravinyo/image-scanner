package transformations

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import utility.ImageUtils

class PerspectiveTransformationTest {
    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given 4 points on color image, it should be tranforms part in 4 points to seprarte image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val sourcePoints = mutableListOf<Point>()
        sourcePoints.add(Point(190.0, 300.0))
        sourcePoints.add(Point(1100.0, 290.0))
        sourcePoints.add(Point(198.0, 957.0))
        sourcePoints.add(Point(1140.0, 957.0))

        val destinationPoints = mutableListOf<Point>()
        destinationPoints.add(Point(0.0, 0.0))
        destinationPoints.add(Point(300.0, 0.0))
        destinationPoints.add(Point(0.0, 300.0))
        destinationPoints.add(Point(300.0, 300.0))

        sourcePoints.forEach{
            Imgproc.circle(input, it, 4, Scalar(0.0, 0.0, 255.0), 4)
        }
        ImageUtils.saveImage("transformation/perspective_1.jpg", input)
        val perspectiveTransformation = PerspectiveTransformation(sourcePoints, destinationPoints)

        val actual = perspectiveTransformation.execute(input)

        assertEquals(Size(300.0, 300.0), actual.size())
        assertEquals(input.type(), actual.type())
        assertEquals(input.channels(), actual.channels())
        ImageUtils.saveImage("transformation/perspective_2.jpg", actual)
    }
}