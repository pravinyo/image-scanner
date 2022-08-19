package filters

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import utility.ImageUtils

class SharpeningFilterTest {
    val sharpeningFilter = SharpeningFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given noisy color image, it should be able to remove noise and return image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val unExpectedImage = input.clone()

        val actual = sharpeningFilter.convert(input)

        assertNotEquals(unExpectedImage, actual)
        ImageUtils.saveImage("filters/sharpening_sample3.jpeg", actual)
    }
}