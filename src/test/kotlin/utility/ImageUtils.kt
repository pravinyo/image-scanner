package utility

import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
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

    fun convertToGrayImage(colorImage: Mat) : Mat {
        val output = Mat()
        Imgproc.cvtColor(colorImage, output, Imgproc.COLOR_BGR2GRAY)
        return output
    }
}