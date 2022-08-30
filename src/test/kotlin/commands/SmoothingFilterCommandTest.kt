package commands

import editor.ImageEditor
import factory.FilterFactory
import filters.SmoothingFilterParameters.*
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SmoothingFilterCommandTest {

    @Test
    fun `it should return smoothing filter operation type`() {
        val imageEditor = mockk<ImageEditor>()
        val parameters = WholeImageDetails(sigmaColor = 1.0, sigmaSpace = 1.0)
        val filterFactory = mockk<FilterFactory>()
        val smoothingFilterCommand = SmoothingFilterCommand(imageEditor, filterFactory, parameters)
        val expectedOperation = OperationType.SmoothingFilter(parameters)

        val actualOperation = smoothingFilterCommand.operationType()

        Assertions.assertEquals(expectedOperation, actualOperation)
    }
}