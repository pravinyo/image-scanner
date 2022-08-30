package commands

import contrastenhancement.ClaheParameters
import editor.ImageEditor
import factory.ContrastEnhancementFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AdaptiveHistogramEnhancementCommandTest {

    @Test
    fun `it should return adaptive histogram equalization filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = ClaheParameters()
        val contrastEnhancementFactory = mockk<ContrastEnhancementFactory>()
        val adaptiveHistogramEnhancementCommand = AdaptiveHistogramEnhancementCommand(imageEditor, contrastEnhancementFactory, parameters)
        val expectedOperation = OperationType.AdaptiveHistogramEnhancement(parameters)

        val actualOperation = adaptiveHistogramEnhancementCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}