package transformations

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

internal class TranslationTransformationTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, It should be able to shift by 100px in x- and 50px in y- direction 2`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val config = TranslationTransformParameters(
            shiftInXDirection = 100.0,
            shiftInYDirection = 50.0
        )
        val translationTransformation = TranslationTransformation(config)

        val actual = translationTransformation.execute(input)

        assertEquals(input.size(), actual.size())
        assertEquals(input.type(), actual.type())
        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("transformation/translation.jpeg", actual)
    }

    @Test
    fun `given color image, It should be able to shift by -100px in x- and -50px in y- direction 2`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val config = TranslationTransformParameters(
            shiftInXDirection = -100.0,
            shiftInYDirection = -50.0
        )
        val translationTransformation = TranslationTransformation(config)

        val actual = translationTransformation.execute(input)

        assertEquals(input.size(), actual.size())
        assertEquals(input.type(), actual.type())
        assertFalse(areEqual(input, actual))
        ImageUtils.saveImage("transformation/translation2.jpeg", actual)
    }
}