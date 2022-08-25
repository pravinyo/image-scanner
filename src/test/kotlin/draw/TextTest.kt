package draw

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Point
import org.opencv.core.Scalar
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

internal class TextTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should be able add text to the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            bottomLeftCorner = Point(112.0, 148.0),
            color = Scalar(255.0, 0.0, 0.0),
            bottomLeftOrigin = true
        )
        val text = Text(parameters)

        val actual = text.addTo(input)

        assertFalse(areEqual(input, actual))
        assertEquals(input.size(), actual.size())
        assertEquals(input.channels(), actual.channels())
        assertEquals(input.type(), actual.type())
        ImageUtils.saveImage("draw/text_1.jpg", actual)
    }

    @Test
    fun `it should be able add text to the top right image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            bottomLeftCorner = Point(input.cols()-100.0, 0.0),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addTo(input)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_2.jpg", actual)
    }

    @Test
    fun `it should be able add text to the top left image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            bottomLeftCorner = Point(0.0, 0.0),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addTo(input)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_3.jpg", actual)
    }

    @Test
    fun `it should be able add text to the bottom right image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            bottomLeftCorner = Point(1300.0, 1000.0),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addTo(input)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_3.jpg", actual)
    }

    @Test
    fun `it should be able add text to the bottom left image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            bottomLeftCorner = Point(-100.0, 1000.0),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addTo(input)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_3.jpg", actual)
    }

    @Test
    fun `it should be able add text with shapes to the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            thickness = 2,
            bottomLeftCorner = Point(input.rows() / 2.0, input.cols() / 2.0),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addRectangleAroundText(input)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_4.jpg", actual)
    }

    @Test
    fun `it should be able add text with 30 degree rotation to the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            thickness = 2,
            bottomLeftCorner = Point(input.rows() / 2.0, input.cols() / 2.0 - 300),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addRotatedTextWithRectangle(input, 30.0)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_5.jpg", actual)
    }

    @Test
    fun `it should be able add text with 30 degree rotation and do perspective transform to the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val parameters = TextParameters(
            text = "I am Pravin Tripathi",
            fontFace = Core.FONT_HERSHEY_SCRIPT_COMPLEX,
            fontScale = 2.0,
            thickness = 2,
            bottomLeftCorner = Point(input.rows() / 2.0, input.cols() / 2.0 - 300),
            color = Scalar(255.0, 0.0, 0.0)
        )
        val text = Text(parameters)

        val actual = text.addTextWithTransformation(input)

//        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("draw/text_6.jpg", actual)
    }
}