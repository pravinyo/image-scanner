import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import utility.AssertionsUtil.areEqual
import utility.ImageUtils

class ImageEditorTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `when no operation performed it should return original image as active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = StateManager()
        val backupManager = mockk<BackupManager>()
        val imageEditor = ImageEditor(input, stateManager, backupManager)

        val activeImage = imageEditor.getActiveImage()

        assertTrue(areEqual(input, activeImage))
    }

    @Test
    fun `it should be able to set new image as active image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val newActiveImage: Mat = ImageUtils.loadImage("input/sample_2.jpeg")
        val stateManager = StateManager()
        val backupManager = mockk<BackupManager>()
        val imageEditor = ImageEditor(input, stateManager, backupManager)

        imageEditor.setActiveImage(newActiveImage)
        val actualActiveImage = imageEditor.getActiveImage()

        assertTrue(areEqual(newActiveImage, actualActiveImage))
        assertFalse(areEqual(input, actualActiveImage))
    }

    @Test
    fun `it should be able to reset operation list for the image`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = mockk<StateManager>(relaxed = true)
        val backupManager = mockk<BackupManager>()
        val imageEditor = ImageEditor(input, stateManager, backupManager)

        every { stateManager.initialize(any()) } returns Unit
        val operationList = listOf("RotationOperation")

        imageEditor.resetOperationList(operationList)

        verify(exactly = 1) {
            stateManager.resetOperationsInfo(operationList)
        }
    }

    @Test
    fun `it should be able to create snapshot of current state`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = mockk<StateManager>(relaxed = true)
        val backupManager = mockk<BackupManager>()
        val imageEditor = ImageEditor(input, stateManager, backupManager)

        every { stateManager.initialize(any()) } returns Unit
        every { stateManager.getOperationsInfo() } returns emptyList()
        every { stateManager.getActiveImage() } returns input.clone()
        val expectedSnapshot = Snapshot(emptyList(), input, imageEditor)

        val actualSnapshot = imageEditor.createSnapshot()

        assertEquals(expectedSnapshot.getOperations(), actualSnapshot.getOperations())
        assertTrue(areEqual(expectedSnapshot.activeImage(), actualSnapshot.activeImage()))
    }

    @Test
    fun `it should add snapshot to the backup`() {
        val input: Mat = ImageUtils.loadImage("input/sample.jpeg")
        val stateManager = mockk<StateManager>(relaxed = true)
        val backupManager = mockk<BackupManager>()
        val imageEditor = ImageEditor(input, stateManager, backupManager)

        every { stateManager.initialize(any()) } returns Unit
        every { stateManager.getOperationsInfo() } returns emptyList()
        every { stateManager.getActiveImage() } returns input.clone()

        val snapshotSlot = slot<Snapshot>()
        every { backupManager.add(capture(snapshotSlot)) } returns Unit

        imageEditor.takeCommand()

        assertEquals(emptyList<String>(), snapshotSlot.captured.getOperations())
        assertTrue(areEqual(input, snapshotSlot.captured.activeImage()))
    }
}