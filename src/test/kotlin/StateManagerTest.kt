import editor.StateManager
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import transformations.FixedRotationDirection.*
import transformations.RotationTransformParameters.*
import utility.AssertionsUtil
import utility.ImageUtils
import utils.OperationType

internal class StateManagerTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should return same image when no operation done`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()

        stateManager.setActiveImage(input)
        val actualImage = stateManager.getActiveImage()

        assertTrue(AssertionsUtil.areEqual(input, actualImage))
    }

    @Test
    fun `when initialize the image and use that as active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()

        stateManager.initialize(input)
        val actualImage = stateManager.getActiveImage()

        assertTrue(AssertionsUtil.areEqual(input, actualImage))
    }

    @Test
    fun `when initialized, operations list should be empty`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()

        stateManager.initialize(input)
        val actualOperations = stateManager.getOperationsInfo()

        assertEquals(emptyList<List<String>>(), actualOperations)
    }

    @Test
    fun `it should store operation performed on image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()
        stateManager.initialize(input)

        val rotationOperation = OperationType.RotationTransform(
            FixedDirection(DIRECTION_CLOCKWISE_90)
        )
        stateManager.appendOperationInfo(rotationOperation)
        val actualOperations = stateManager.getOperationsInfo()

        assertNotEquals(emptyList<List<String>>(), actualOperations)
    }

    @Test
    fun `it should reset operation info`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()
        stateManager.initialize(input)
        val rotationOperation = OperationType.RotationTransform(
            FixedDirection(DIRECTION_CLOCKWISE_90)
        )
        stateManager.appendOperationInfo(rotationOperation)
        val operationList = emptyList<OperationType>()


        stateManager.resetOperationsInfo(operationList)
        val actualOperations = stateManager.getOperationsInfo()

        assertEquals(emptyList<List<String>>(), actualOperations)
    }
}