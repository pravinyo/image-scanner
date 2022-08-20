package filters

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

class UnsharpMaskBoostFilterTest {

    val config = UnsharpMaskConfig(kernelSize = 21.0, sigma = 3.0, boostAmount = 1.5)
    val unsharpMaskBoostFilter = UnsharpMaskBoostFilter(config)

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to sharpen the image` () {
        val input = ImageUtils.loadImage("input/sample.jpeg")

        val actual = unsharpMaskBoostFilter.convert(input)

        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("filters/boosting_final.jpeg", actual)
    }
}