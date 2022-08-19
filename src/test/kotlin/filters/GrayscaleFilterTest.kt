package filters

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.ImageUtils

internal class GrayscaleFilterTest {

    val grayscaleFilter = GrayscaleFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to convert to grayscale image`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val unExpectedImage = input.clone()

        val actual = grayscaleFilter.convert(input)

        assertNotEquals(unExpectedImage, actual)
        ImageUtils.saveImage("filters/grayscale_sample.jpeg", actual)
    }
}