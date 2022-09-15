import editor.BackupManager
import editor.ImageEditor
import editor.Snapshot
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat
import transformations.FixedRotationDirection.*
import transformations.RotationTransformParameters.*
import utils.OperationType

class BackupManagerTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should be able to add snapshot`() {
        val image = Mat()
        val imageEditor = mockk<ImageEditor>()
        val snapshot = Snapshot(mockk(), image, imageEditor)
        val backupManager = BackupManager()

        backupManager.add(snapshot)
        val actualSize = backupManager.size

        assertEquals(1, actualSize)
    }

    @Test
    fun `it should be able to remove last snapshot and run`() {
        val image = Mat()
        val imageEditor = mockk<ImageEditor>()
        val rotationOperation = OperationType.RotationTransform(
            FixedDirection(DIRECTION_CLOCKWISE_90)
        )
        val snapshot = Snapshot(listOf(rotationOperation), image, imageEditor)
        val spySnapshot = spyk(snapshot)

        val backupManager = BackupManager()
        backupManager.add(spySnapshot)

        justRun { imageEditor.setActiveImage(any()) }
        justRun { imageEditor.resetOperationList(any()) }

        val response = backupManager.runLastSnapshot()
        assertEquals(true, response)

        verify(exactly = 1) {
            spySnapshot.restore()
        }
    }

}