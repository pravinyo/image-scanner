package commands

import editor.ImageEditor
import factory.FilterFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.OperationType

internal class GrayscaleFilterCommandTest {

    @Test
    fun `it should return grayscale filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val filterFactory = mockk<FilterFactory>()
        val grayscaleFilterCommand = GrayscaleFilterCommand(imageEditor, filterFactory)
        val expectedOperation = OperationType.GrayscaleFilter

        val actualOperation = grayscaleFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}