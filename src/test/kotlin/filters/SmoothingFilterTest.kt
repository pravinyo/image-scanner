package filters

import filters.SmoothingFilterParameters.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Rect
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

internal class SmoothingFilterTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given noisy color image, it should be able to smooth the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val unExpectedImage = input.clone()
        val smoothingFilter = SmoothingFilter(
            WholeImageDetails(
                pixelNeighborDiameter = 9,
                sigmaColor = 75.0,
                sigmaSpace = 75.0
            )
        )

        val actual = smoothingFilter.convert(input)

        assertFalse(areEqual(unExpectedImage, actual))
    }

    @Test
    fun `given color image, it should be able to smooth specific region in the image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val regionToSmooth = Rect(input.rows().times(0.8f).toInt(), 100, 200, 200)
        val regionNotToSmooth = Rect(input.rows() / 2, 300, 200, 200)
        val smoothingFilter = SmoothingFilter(
            PartialImageDetails(
                regionToSmooth = regionToSmooth,
                kernelSize = 21,
                sigmaX = 10.0,
                sigmaY = 10.0
            )
        )

        val actual = smoothingFilter.convert(input)

        assertTrue(areEqual(input.submat(regionNotToSmooth), actual.submat(regionNotToSmooth)))
        assertFalse(areEqual(input.submat(regionToSmooth), actual.submat(regionToSmooth)))
    }
}