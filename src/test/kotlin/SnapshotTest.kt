import editor.ImageEditor
import editor.Snapshot
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import transformations.FixedRotationDirection
import transformations.RotationTransformParameters
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

class SnapshotTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should store list of operation`() {
        val operationList = listOf(OperationType.RotationTransform(
            RotationTransformParameters.FixedDirection(FixedRotationDirection.DIRECTION_CLOCKWISE_90)
        ))
        val snapshot = Snapshot(operationList, Mat(), mockk())

        val actualOperations = snapshot.getOperations()

        Assertions.assertEquals(operationList, actualOperations)
    }

    @Test
    fun `it should store active image`() {
        val activeImage = ImageUtils.loadImage("input/sample.jpeg")
        val snapshot = Snapshot(emptyList(), activeImage.clone(), mockk())

        val actualActiveImage = snapshot.activeImage()

        assertTrue(areEqual(activeImage, actualActiveImage))
    }

    @Test
    fun `it should restore image editor state`() {
        val activeImage = ImageUtils.loadImage("input/sample.jpeg")
        val operationList = listOf(OperationType.RotationTransform(
            RotationTransformParameters.FixedDirection(FixedRotationDirection.DIRECTION_CLOCKWISE_90)
        ))
        val editor = mockk<ImageEditor>()
        val snapshot = Snapshot(operationList, activeImage, editor)

        every { editor.setActiveImage(any()) } returns Unit
        every { editor.resetOperationList(any()) } returns Unit

        snapshot.restore()

        verify(exactly = 1) {
            editor.setActiveImage(activeImage)
            editor.resetOperationList(operationList)
        }
    }
}