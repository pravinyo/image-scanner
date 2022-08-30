package commands

import editor.ImageEditor
import factory.TransformationFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import transformations.TranslationTransformParameters

class TranslateCommandTest {
    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should return translation operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val translationParameters = TranslationTransformParameters(
            shiftInYDirection = 10.0,
            shiftInXDirection = 20.0
        )
        val transformationFactory = mockk<TransformationFactory>()
        val expectedOperation = OperationType.TranslationTransform(translationParameters)

        val translateCommand = TranslateCommand(imageEditor, transformationFactory, translationParameters)
        val actualOperation = translateCommand.operationType()

        assertEquals(expectedOperation, actualOperation)
    }
}