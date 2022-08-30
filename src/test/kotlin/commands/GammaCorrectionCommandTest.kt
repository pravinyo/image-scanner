package commands

import editor.ImageEditor
import factory.ContrastEnhancementFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GammaCorrectionCommandTest {

     @Test
     fun `it should return gamma correction filter operation type`() {
         val imageEditor = mockk<ImageEditor>()
         val gamma = 0.76
         val contrastEnhancementFactory = mockk<ContrastEnhancementFactory>()
         val correctionCommand = GammaCorrectionCommand(imageEditor, contrastEnhancementFactory, gamma)
         val expectedOperation = OperationType.GammaEnhancement(gamma)

         val actualOperation = correctionCommand.operationType()

         Assertions.assertEquals(expectedOperation, actualOperation)
     }
 }