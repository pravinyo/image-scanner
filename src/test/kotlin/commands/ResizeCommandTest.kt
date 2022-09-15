package commands

import editor.ImageEditor
import factory.TransformationFactory
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import transformations.ScalingTransformParameters
import transformations.ScalingTransformation
import utils.OperationType

class ResizeCommandTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should be resize image and set to editor`() {
        val image = Mat.zeros(Size(10.0, 10.0), CvType.CV_16SC3)
        val imageEditor = mockk<ImageEditor>()
        val parameters = ScalingTransformParameters(outputSize = Size(image.rows() / 2.0, image.cols() / 2.0))
        val transformationFactory = mockk<TransformationFactory>()
        val scalingTransformation = mockk<ScalingTransformation>()

        val resizeCommand = ResizeCommand(imageEditor, transformationFactory, parameters)

        every { imageEditor.getActiveImage() } returns image
        justRun { imageEditor.setActiveImage(any()) }
        every { transformationFactory.createInstance(any()) } returns scalingTransformation
        every {
            scalingTransformation.execute(any())
        } returns image.submat(0, image.rows() / 2, 0, image.cols() / 2)

        resizeCommand.execute()

        verify {
            scalingTransformation.execute(image)
            imageEditor.setActiveImage(any())
        }
    }

    @Test
    fun `it should return scaling operation type`() {
        val image = Mat.zeros(Size(10.0, 10.0), CvType.CV_16SC3)
        val imageEditor = mockk<ImageEditor>()
        val parameters = ScalingTransformParameters(outputSize = Size(image.rows() / 2.0, image.cols() / 2.0))
        val transformationFactory = mockk<TransformationFactory>()
        val expectedOperation = OperationType.ScalingTransform(parameters)
        val resizeCommand = ResizeCommand(imageEditor, transformationFactory, parameters)

        val actualOperation = resizeCommand.operationType()

        assertEquals(expectedOperation, actualOperation)
    }
}