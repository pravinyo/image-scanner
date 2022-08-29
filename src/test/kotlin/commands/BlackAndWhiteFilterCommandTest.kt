package commands

import editor.ImageEditor
import factory.FilterFactory
import filters.BlackAndWhiteFilter
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
        val filterFactory = mockk<FilterFactory>()
        val blackAndWhiteFilter = mockk<BlackAndWhiteFilter>()
        val backAndWhiteCommand = BlackAndWhiteFilterCommand(imageEditor, filterFactory, parameters)

        every { imageEditor.getActiveImage() } returns image
        justRun { imageEditor.setActiveImage(any()) }
        every { filterFactory.createInstance(any()) } returns blackAndWhiteFilter
        every { blackAndWhiteFilter.convert(any()) } returns image.clone()

        backAndWhiteCommand.execute()

        verify(exactly = 1) {
            blackAndWhiteFilter.convert(image)
            imageEditor.setActiveImage(any())
        }
    }

    @Test
    fun `it should return black and white filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = BlackAndWhiteFilterParameters()
        val filterFactory = mockk<FilterFactory>()
        val blackAndWhiteFilterCommand = BlackAndWhiteFilterCommand(imageEditor, filterFactory, parameters)
        val expectedOperation = OperationType.BlackAndWhiteFilter(parameters)

        val actualOperation = blackAndWhiteFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}