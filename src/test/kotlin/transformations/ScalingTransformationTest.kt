package transformations

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.ImageUtils

internal class ScalingTransformationTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, It should be able to 2 times of its current size`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val scalingTransformation = ScalingTransformation()

        val actual = scalingTransformation.execute(input)

        assertEquals(input.size().width * 2, actual.size().width)
        assertEquals(input.size().height * 2, actual.size().height)
        assertEquals(input.type(), actual.type())
        ImageUtils.saveImage("transformation/scaling_up_inter_cubic.jpg", actual)
    }
}