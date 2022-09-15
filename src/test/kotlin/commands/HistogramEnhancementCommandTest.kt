package commands

import editor.ImageEditor
import factory.ContrastEnhancementFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.OperationType

class HistogramEnhancementCommandTest {

    @Test
    fun `it should return histogram equalization filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val contrastEnhancementFactory = mockk<ContrastEnhancementFactory>()
        val adaptiveHistogramEnhancementCommand = HistogramEnhancementCommand(imageEditor, contrastEnhancementFactory)
        val expectedOperation = OperationType.HistogramEnhancement

        val actualOperation = adaptiveHistogramEnhancementCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}