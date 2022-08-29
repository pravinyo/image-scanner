package commands

import editor.ImageEditor
import factory.TransformationFactory
import filters.BlackAndWhiteFilter
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import transformations.FixedRotationDirection
import transformations.RotationTransformParameters.*
import transformations.RotationTransformation

internal class RotateCommandTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should be rotate image and set to editor`() {
        val image = Mat.zeros(Size(10.0, 10.0), CvType.CV_16SC3)
        val imageEditor = mockk<ImageEditor>()
        val parameters = FixedDirection(FixedRotationDirection.DIRECTION_CLOCKWISE_90)
        val transformationFactory = mockk<TransformationFactory>()
        val rotationTransform = mockk<RotationTransformation>()
        val rotateCommand = RotateCommand(imageEditor,transformationFactory, parameters)

        every { imageEditor.getActiveImage() } returns image
        justRun { imageEditor.setActiveImage(any()) }
        every { transformationFactory.createInstance(any()) } returns rotationTransform
        every { rotationTransform.execute(any()) } returns image.clone()

        rotateCommand.execute()

        verify {
            rotationTransform.execute(image)
            imageEditor.setActiveImage(any())
        }
    }

    @Test
    fun `it should return rotation transform operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = FixedDirection(FixedRotationDirection.DIRECTION_CLOCKWISE_90)
        val transformationFactory = mockk<TransformationFactory>()
        val rotateCommand = RotateCommand(imageEditor, transformationFactory, parameters)
        val expectedOperation = OperationType.RotationTransform(parameters)

        val actualOperation = rotateCommand.operationType()

        assertEquals(expectedOperation, actualOperation)
    }
}