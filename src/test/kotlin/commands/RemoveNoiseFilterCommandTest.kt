package commands

import editor.ImageEditor
import factory.FilterFactory
import filters.RemoveNoiseFilterParameters
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.OperationType

class RemoveNoiseFilterCommandTest {

    @Test
    fun `it should return remove noise filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = RemoveNoiseFilterParameters()
        val filterFactory = mockk<FilterFactory>()
        val removeNoiseFilterCommand = RemoveNoiseFilterCommand(imageEditor, filterFactory, parameters)
        val expectedOperation = OperationType.RemoveNoiseFilter(parameters)

        val actualOperation = removeNoiseFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}