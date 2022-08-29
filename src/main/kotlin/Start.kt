import commands.BlackAndWhiteFilterCommand
import commands.RotateCommand
import editor.BackupManager
import editor.ImageEditor
import editor.StateManager
import filters.BlackAndWhiteFilterParameters
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.imread
import transformations.FixedRotationDirection
import transformations.RotationTransformParameters
import java.io.File

object Start {

    @JvmStatic
    fun main(args: Array<String>) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val path = File("src/main/resources").absolutePath
        val image = imread("$path/images/sample.png")

        val stateManager = StateManager()
        val backupManager = BackupManager()
        val imageEditor = ImageEditor(
            image = image,
            stateManager = stateManager,
            backupManager = backupManager
        )

        val rotationCommand1 = RotateCommand(
            imageEditor,
            RotationTransformParameters.FixedDirection(FixedRotationDirection.DIRECTION_180)
        )
        val rotationCommand2 = RotateCommand(
            imageEditor,
            RotationTransformParameters.ArbitraryDirection(30.0)
        )

        val blackAndWhiteFilterCommand = BlackAndWhiteFilterCommand(
            imageEditor,
            BlackAndWhiteFilterParameters()
        )

        imageEditor.takeCommand(rotationCommand1)
        saveImage("sample_1.png", imageEditor.getActiveImage())

        imageEditor.takeCommand(blackAndWhiteFilterCommand)
        saveImage("sample_2.png", imageEditor.getActiveImage())

        imageEditor.takeCommand(rotationCommand2)
        saveImage("sample_3.png", imageEditor.getActiveImage())

        imageEditor.undoChanges()
        saveImage("sample_4.png", imageEditor.getActiveImage())

        imageEditor.undoChanges()
        saveImage("sample_5.png", imageEditor.getActiveImage())

        imageEditor.undoChanges()
        saveImage("sample_6.png", imageEditor.getActiveImage())

        imageEditor.undoChanges()
        saveImage("sample_7.png", imageEditor.getActiveImage())
    }

    fun saveImage(subPath: String, image: Mat) {
        val path = "src/main/resources"
        val absolutePath = File(path).absolutePath
        Imgcodecs.imwrite("$absolutePath/output/$subPath", image)
    }
}