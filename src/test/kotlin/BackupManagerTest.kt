import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.Mat

class BackupManagerTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `it should be able to add snapshot`() {
        val image = Mat()
        val imageEditor = mockk<ImageEditor>()
        val snapshot = Snapshot(listOf("RotateOperation"), image, imageEditor)
        val backupManager = BackupManager()

        backupManager.add(snapshot)
        val actualSize = backupManager.size

        assertEquals(1, actualSize)
    }
}