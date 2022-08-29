package commands

import ImageEditor
import filters.BlackAndWhiteFilterParameters
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.ImageUtils

class BlackAndWhiteFilterCommandTest {
    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should convert image to black and white, and set to editor`() {
        val image = ImageUtils.loadImage("input/sample.jpeg")
        val imageEditor = mockk<ImageEditor>()
        val parameters = BlackAndWhiteFilterParameters()
        val backAndWhiteCommand = BlackAndWhiteFilterCommand(imageEditor, parameters)

        every { imageEditor.getActiveImage() } returns image
        justRun { imageEditor.setActiveImage(any()) }

        backAndWhiteCommand.execute()

        verify {
            imageEditor.setActiveImage(any())
        }
    }

    @Test
    fun `it should return black and white filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = BlackAndWhiteFilterParameters()
        val blackAndWhiteFilterCommand = BlackAndWhiteFilterCommand(imageEditor, parameters)
        val expectedOperation = OperationType.BlackAndWhiteFilter(parameters)

        val actualOperation = blackAndWhiteFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}