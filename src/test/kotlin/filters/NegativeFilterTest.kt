package filters

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.File


class NegativeFilterTest {

    val negativeFilter = NegativeFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to convert to negative image`() {
        val path = "src/test/resources"
        val absolutePath = File(path).absolutePath
        val input: Mat = Imgcodecs.imread("$absolutePath/input/sample.jpeg")
        val unExpectedOutput = input.clone()

        val actual = negativeFilter.convert(input)

        Assertions.assertNotEquals(unExpectedOutput, actual)
        Imgcodecs.imwrite("$absolutePath/output/filters/negative/sample.jpg", actual)
    }
}