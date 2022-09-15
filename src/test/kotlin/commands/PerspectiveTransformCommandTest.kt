package commands

import editor.ImageEditor
import factory.TransformationFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.*
import utils.OperationType
import utils.Point2D

class PerspectiveTransformCommandTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should return perspective operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val sourcePoints = emptyList<Point2D>()
        val transformationFactory = mockk<TransformationFactory>()

        val expectedOperation = OperationType.PerspectiveTransform(sourcePoints)

        val perspectiveTransformCommand = PerspectiveTransformCommand(imageEditor, transformationFactory, sourcePoints)

        val actualOperation = perspectiveTransformCommand.operationType()

        assertEquals(expectedOperation, actualOperation)
    }
}