package commands

import editor.ImageEditor
import factory.FilterFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.OperationType

class NegativeFilterCommandTest {

    @Test
    fun `it should return negative filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val filterFactory = mockk<FilterFactory>()
        val negativeFilterCommand = NegativeFilterCommand(imageEditor, filterFactory)
        val expectedOperation = OperationType.NegativeFilter

        val actualOperation = negativeFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}