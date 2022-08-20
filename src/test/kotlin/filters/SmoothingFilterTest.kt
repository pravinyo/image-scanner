package filters

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Rect
import utility.AssertionsUtil.areEqual
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

        assertFalse(areEqual(unExpectedImage, actual))
    }

    @Test
    fun `given color image, it should be able to smooth specific region in the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val regionToSmooth = Rect(input.rows().times(0.8f).toInt(), 100, 200, 200)
        val regionNotToSmooth = Rect(input.rows() / 2, 300, 200, 200)

        smoothingFilter.setRegion(regionToSmooth)
        val actual = smoothingFilter.convert(input)

        assertTrue(areEqual(input.submat(regionNotToSmooth), actual.submat(regionNotToSmooth)))
        assertFalse(areEqual(input.submat(regionToSmooth), actual.submat(regionToSmooth)))
    }
}