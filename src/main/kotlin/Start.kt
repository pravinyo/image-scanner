import commands.BlackAndWhiteFilterCommand
import commands.PerspectiveTransformCommand
import commands.RotateCommand
import commands.UnsharpMaskBoostFilterCommand
import editor.BackupManager
import editor.ImageEditor
import editor.StateManager
import factory.ContrastEnhancementFactory
import factory.FilterFactory
import factory.TransformationFactory
import filters.BlackAndWhiteFilterParameters
import filters.UnsharpMaskParameters
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.imread
import transformations.RotationTransformParameters
import java.io.File

object Start {

    @JvmStatic
    fun main(args: Array<String>) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val path = File("src/main/resources").absolutePath
        val image = imread("$path/images/sample 2.jpeg")

        val transformationFactory = TransformationFactory()
        val contrastEnhancementFactory = ContrastEnhancementFactory()
        val filterFactory = FilterFactory(contrastEnhancementFactory)

        val stateManager = StateManager()
        val backupManager = BackupManager()
        val imageEditor = ImageEditor(
            image = image,
            stateManager = stateManager,
            backupManager = backupManager
        )

        val sourcePoints = mutableListOf<Point>()
        sourcePoints.add(Point(190.0, 300.0))
        sourcePoints.add(Point(1100.0, 290.0))
        sourcePoints.add(Point(198.0, 957.0))
        sourcePoints.add(Point(1140.0, 957.0))

        val perspectiveTransformCommand = PerspectiveTransformCommand(
            imageEditor = imageEditor,
            transformationFactory = transformationFactory,
            sourcePoints = sourcePoints
        )
        imageEditor.takeCommand(perspectiveTransformCommand)
        saveImage("sample_0.png", imageEditor.getActiveImage())


        val boostFilterCommand = UnsharpMaskBoostFilterCommand(
            imageEditor,
            filterFactory,
            UnsharpMaskParameters(kernelSize = 21.0, sigma = 3.0, boostAmount = 1.5)
        )
        imageEditor.takeCommand(boostFilterCommand)
        saveImage("sample_1.png", imageEditor.getActiveImage())


        val rotationCommand2 = RotateCommand(
            imageEditor,
            transformationFactory,
            RotationTransformParameters.ArbitraryDirection(30.0)
        )
        imageEditor.takeCommand(rotationCommand2)
        saveImage("sample_3.png", imageEditor.getActiveImage())


        val blackAndWhiteFilterCommand = BlackAndWhiteFilterCommand(
            imageEditor,
            filterFactory,
            BlackAndWhiteFilterParameters()
        )
        imageEditor.takeCommand(blackAndWhiteFilterCommand)
        saveImage("sample_2.png", imageEditor.getActiveImage())


        //undo back and white filter
        imageEditor.undoChanges()
        saveImage("sample_4.png", imageEditor.getActiveImage())

        // undo rotation
        imageEditor.undoChanges()
        saveImage("sample_5.png", imageEditor.getActiveImage())

        //undo boost filter
        imageEditor.undoChanges()
        saveImage("sample_6.png", imageEditor.getActiveImage())

        //undo perspective transform
        imageEditor.undoChanges()
        saveImage("sample_7.png", imageEditor.getActiveImage())
    }

    fun saveImage(subPath: String, image: Mat) {
        val path = "src/main/resources"
        val absolutePath = File(path).absolutePath
        Imgcodecs.imwrite("$absolutePath/output/$subPath", image)
    }
}