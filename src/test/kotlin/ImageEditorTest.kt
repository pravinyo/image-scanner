import filters.GrayscaleFilter
import filters.RemoveNoiseFilter
import filters.RemoveNoiseFilterParameters
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
    fun `when no operation performed it should return original image as active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()
        val imageEditor = ImageEditor(input, stateManager)

        val activeImage = imageEditor.getActiveImage()

        assertTrue(areEqual(input, activeImage))
    }

    @Test
    fun `it should be able to set new image as active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val newActiveImage: Mat = ImageUtils.loadImage("input/sample_2.jpeg")
        val stateManager = StateManager()
        val imageEditor = ImageEditor(input, stateManager)

        imageEditor.setActiveImage(newActiveImage)
        val actualActiveImage = imageEditor.getActiveImage()

        assertTrue(areEqual(newActiveImage, actualActiveImage))
        assertFalse(areEqual(input, actualActiveImage))
    }
}