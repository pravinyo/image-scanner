package commands

import ImageEditor
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import transformations.FixedRotationDirection
import transformations.RotationTransformParameters.*

internal class RotateCommandTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should be rotate image and set to editor`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = FixedDirection(FixedRotationDirection.DIRECTION_CLOCKWISE_90)
        val rotateCommand = RotateCommand(imageEditor, parameters)

        every { imageEditor.getActiveImage() } returns Mat.zeros(Size(10.0, 10.0), CvType.CV_16SC3)
        justRun { imageEditor.setActiveImage(any()) }

        rotateCommand.execute()

        verify {
            imageEditor.setActiveImage(any())
        }
    }
}