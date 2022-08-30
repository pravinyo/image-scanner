package commands

import contrastenhancement.ImageContrastAdjustParameters
import editor.ImageEditor
import factory.ContrastEnhancementFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ImageContrastAdjustCommandTest {

    @Test
    fun `it should return image contrast adjustment filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = ImageContrastAdjustParameters()
        val contrastEnhancementFactory = mockk<ContrastEnhancementFactory>()
        val adaptiveHistogramEnhancementCommand = ImageContrastAdjustCommand(imageEditor, contrastEnhancementFactory, parameters)
        val expectedOperation = OperationType.ImAdjustEnhancement(parameters)

        val actualOperation = adaptiveHistogramEnhancementCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}