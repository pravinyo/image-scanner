package filters

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.ImageUtils

internal class SmoothingFilterTest {

    val smoothingFilter = SmoothingFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given noisy color image, it should be able to smooth the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val unExpectedImage = input.clone()

        val actual = smoothingFilter.convert(input)

        assertNotEquals(unExpectedImage, actual)
        ImageUtils.saveImage("filters/smoothing_sample4.jpeg", actual)
    }
}