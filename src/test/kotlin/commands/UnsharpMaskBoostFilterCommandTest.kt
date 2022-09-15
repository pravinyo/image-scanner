package commands

import editor.ImageEditor
import factory.FilterFactory
import filters.UnsharpMaskParameters
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.OperationType

class UnsharpMaskBoostFilterCommandTest {

     @Test
     fun `it should return unsharp boost mask filter operation type`() {
         val imageEditor = mockk<ImageEditor>()
         val parameters = UnsharpMaskParameters(kernelSize = 3.0, sigma = 0.0, boostAmount = 2.0 )
         val filterFactory = mockk<FilterFactory>()
         val boostFilterCommand = UnsharpMaskBoostFilterCommand(imageEditor, filterFactory, parameters)
         val expectedOperation = OperationType.UnsharpMaskBoostFilter(parameters)

         val actualOperation = boostFilterCommand.operationType()

         Assertions.assertEquals(expectedOperation, actualOperation)
     }
 }