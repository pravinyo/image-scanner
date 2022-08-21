package transformations

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

class RotationTransformationTest {

    val rotationTransformation = RotationTransformation()

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to rotate by 90 degree clockwise`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val rotatedImage = Mat()
        Core.rotate(input, rotatedImage, Core.ROTATE_90_CLOCKWISE)

        val actual = rotationTransformation.execute(input)

        assertTrue(areEqual(rotatedImage, actual))
        assertEquals(input.type(), actual.type())
        assertTrue(input.size().height.compareTo(actual.size().width) == 0)
        assertTrue(input.size().width.compareTo(actual.size().height) == 0)
        ImageUtils.saveImage("transformation/rotate.jpeg", actual)
    }

}