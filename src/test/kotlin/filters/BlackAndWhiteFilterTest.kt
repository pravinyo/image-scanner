package filters

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import utility.ImageUtils

class BlackAndWhiteFilterTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to convert to black and white type`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val blackAndWhiteFilter = BlackAndWhiteFilter(BlackAndWhiteFilterParameters())

        val actual = blackAndWhiteFilter.convert(input)

        assertEquals(input.size(), actual.size())
        assertNotEquals(input.channels(), actual.channels())
        assertEquals(CvType.CV_8U, actual.type())
    }
}