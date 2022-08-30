package commands

import editor.ImageEditor
import factory.FilterFactory
import filters.SharpeningFilterParameters
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SharpeningFilterCommandTest {
    @Test
    fun `it should return sharpening filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = SharpeningFilterParameters()
        val filterFactory = mockk<FilterFactory>()
        val sharpeningFilterCommand = SharpeningFilterCommand(imageEditor, filterFactory, parameters)
        val expectedOperation = OperationType.SharpeningFilter(parameters)

        val actualOperation = sharpeningFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}