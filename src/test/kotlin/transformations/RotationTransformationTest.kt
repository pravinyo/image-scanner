package transformations

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import transformations.FixedRotationDirection.*
import transformations.RotationTransformParameters.*
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

class RotationTransformationTest {
    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given color image, it should be able to rotate by 90 degree clockwise`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val expectedImage = Mat()
        Core.rotate(input, expectedImage, Core.ROTATE_90_CLOCKWISE)
        val rotationTransformation = RotationTransformation(FixedDirection(DIRECTION_CLOCKWISE_90))

        val actual = rotationTransformation.execute(input)

        assertTrue(areEqual(expectedImage, actual))
        assertEquals(input.type(), actual.type())
        assertTrue(input.size().height.compareTo(actual.size().width) == 0)
        assertTrue(input.size().width.compareTo(actual.size().height) == 0)
    }

    @Test
    fun `given color image, it should be able to rotate by 180 degree`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val expectedImage = Mat()
        Core.rotate(input, expectedImage, Core.ROTATE_180)
        val rotationTransformation = RotationTransformation(FixedDirection(DIRECTION_180))

        val actual = rotationTransformation.execute(input)

        assertTrue(areEqual(expectedImage, actual))
        assertEquals(input.type(), actual.type())
        assertTrue(input.size().height.compareTo(actual.size().height) == 0)
        assertTrue(input.size().width.compareTo(actual.size().width) == 0)
    }

    @Test
    fun `given color image, it should be able to rotate by 270 degree`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val expectedImage = Mat()
        Core.rotate(input, expectedImage, Core.ROTATE_90_COUNTERCLOCKWISE)
        val rotationTransformation = RotationTransformation(FixedDirection(DIRECTION_CLOCKWISE_270))

        val actual = rotationTransformation.execute(input)

        assertTrue(areEqual(expectedImage, actual))
        assertEquals(input.type(), actual.type())
        assertTrue(input.size().height.compareTo(actual.size().width) == 0)
        assertTrue(input.size().width.compareTo(actual.size().height) == 0)
    }

    @Test
    fun `given color image, it should be able to rotate by 30 degree clockwise`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val rotationTransformation = RotationTransformation(ArbitraryDirection(angle = 30.0, scale = 0.6))

        val actual = rotationTransformation.execute(input)

        assertFalse(areEqual(input, actual))
        assertEquals(input.type(), actual.type())
        ImageUtils.saveImage("transformation/rotation_scale_1.jpeg", actual)
    }

}