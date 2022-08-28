import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

class ImageEditorTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should return active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val imageEditor = ImageEditor(input)

        val activeImage = imageEditor.activeImage()

        assertTrue(areEqual(input, activeImage))
    }
}