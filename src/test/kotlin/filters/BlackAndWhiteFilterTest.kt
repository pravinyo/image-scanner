package filters

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.ImageUtils

class BlackAndWhiteFilterTest {

    val blackAndWhiteFilter = BlackAndWhiteFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to convert to black and white type`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val unExpected = input.clone()

        val actual = blackAndWhiteFilter.convert(input)

        assertNotEquals(unExpected, actual)
        ImageUtils.saveImage("filters/blackAndWhite_sample3.jpeg", actual)
    }
}