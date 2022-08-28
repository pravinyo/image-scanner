import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import utility.AssertionsUtil
import utility.ImageUtils

internal class StateManagerTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should return same image when no operation done`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()

        stateManager.setActiveImage(input)
        val actualImage = stateManager.getActiveImage()

        assertTrue(AssertionsUtil.areEqual(input, actualImage))
    }

    @Test
    fun `when initialize the image and use that as active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()

        stateManager.initialize(input)
        val actualImage = stateManager.getActiveImage()

        assertTrue(AssertionsUtil.areEqual(input, actualImage))
    }
}