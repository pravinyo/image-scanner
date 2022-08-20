package filters

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.AssertionsUtil
import utility.ImageUtils

internal class RemoveNoiseFilterTest {

    val removeNoiseFilter = RemoveNoiseFilter()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given noisy color image, it should be able to remove noise and return image`() {
        val input = ImageUtils.loadImage("input/noisy.png")
        val unExpectedImage = input.clone()

        val actual = removeNoiseFilter.convert(input)

        assertFalse(AssertionsUtil.areEqual(unExpectedImage, actual))
    }
}