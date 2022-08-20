package filters

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import utility.AssertionsUtil
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

        val actual = grayscaleFilter.convert(input)

        assertEquals(input.size(), actual.size())
        assertNotEquals(input.channels(), actual.channels())
        assertEquals(CvType.CV_8UC1, actual.type())
    }
}