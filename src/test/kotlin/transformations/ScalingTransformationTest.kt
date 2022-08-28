package transformations

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Size
import utility.ImageUtils

internal class ScalingTransformationTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, It should be able to convert 2 times of its current size`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val config = ScalingTransformParameters(
            outputSize = Size(input.size().width * 2, input.size().height * 2),
            scalingType = ScalingType.SCALE_UP
        )
        val scalingTransformation = ScalingTransformation(config)

        val actual = scalingTransformation.execute(input)

        assertEquals(input.size().width * 2, actual.size().width)
        assertEquals(input.size().height * 2, actual.size().height)
        assertEquals(input.type(), actual.type())
    }

    @Test
    fun `given color image, It should be able to convert half size of its current size`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val config = ScalingTransformParameters(
            outputSize = Size(input.size().width * 0.5, input.size().height * 0.5)
        )
        val scalingTransformation = ScalingTransformation(config)

        val actual = scalingTransformation.execute(input)

        assertEquals(input.size().width * 0.5, actual.size().width)
        assertEquals(input.size().height * 0.5, actual.size().height)
        assertEquals(input.type(), actual.type())
    }

    @Test
    fun `given color image, It should be able to scale in x and y direction`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val config = ScalingTransformParameters(
            scaleInXDirection = 1.1,
            scaleInYDirection = 1.2,
            scalingType = ScalingType.SCALE_UP
        )
        val scalingTransformation = ScalingTransformation(config)

        val actual = scalingTransformation.execute(input)

        assertTrue((1408.0).compareTo(actual.size().width) == 0)
        assertTrue((1152.0).compareTo(actual.size().height) == 0)
        assertEquals(input.type(), actual.type())
    }
}