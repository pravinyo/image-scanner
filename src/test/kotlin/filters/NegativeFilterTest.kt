package filters

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import utility.ImageUtils



class NegativeFilterTest {

    val negativeFilter = NegativeFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to convert to negative image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val unExpectedOutput = input.clone()

        val actual = negativeFilter.convert(input)

        Assertions.assertNotEquals(unExpectedOutput, actual)
        ImageUtils.saveImage("filters/negative_sample.jpg", actual)
    }
}