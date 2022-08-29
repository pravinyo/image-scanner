package factory

import filters.GrayscaleFilter
import filters.SmoothingFilter
import filters.SmoothingFilterParameters
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FilterFactoryTest {

    @Test
    fun `should return instance of grayscale filter`() {
        val filterFactory = FilterFactory()
        val grayscaleOperation = OperationType.GrayscaleFilter

        val grayscaleFilter = filterFactory.createInstance(grayscaleOperation)

        assertTrue(grayscaleFilter is GrayscaleFilter)
    }

    @Test
    fun `should return instance of Smoothing filter`() {
        val filterFactory = FilterFactory()
        val smoothingOperation = OperationType.SmoothingFilter(
            SmoothingFilterParameters.WholeImageDetails(
                sigmaSpace = 1.0,
                sigmaColor = 1.1
            )
        )

        val smoothingFilter = filterFactory.createInstance(smoothingOperation)

        assertTrue(smoothingFilter is SmoothingFilter)
    }
}