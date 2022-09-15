package commands

import contrastenhancement.SaturationCorrectionParameters
import editor.ImageEditor
import factory.ContrastEnhancementFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.OperationType

class SaturationCorrectionCommandTest {

    @Test
    fun `it should return saturation correction filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = SaturationCorrectionParameters(
            alpha = 2.0,
            beta = 30.0
        )
        val contrastEnhancementFactory = mockk<ContrastEnhancementFactory>()
        val saturationCorrectionCommand = SaturationCorrectionCommand(
            imageEditor, contrastEnhancementFactory, parameters
        )
        val expectedOperation = OperationType.SaturationEnhancement(parameters)

        val actualOperation = saturationCorrectionCommand.operationType()

        assertEquals(expectedOperation, actualOperation)
    }
}