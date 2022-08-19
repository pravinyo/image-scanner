package utility

import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.File

object ImageUtils {

    fun loadImage(subPath: String): Mat {
        val path = "src/test/resources"
        val absolutePath = File(path).absolutePath
        return Imgcodecs.imread("$absolutePath/$subPath")
    }

    fun saveImage(subPath: String, image: Mat) {
        val path = "src/test/resources"
        val absolutePath = File(path).absolutePath
        Imgcodecs.imwrite("$absolutePath/output/$subPath", image)
    }
}