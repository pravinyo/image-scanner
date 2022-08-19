package filters

import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class BlackAndWhiteFilter {
    fun convert(input: Mat): Mat {
        val output = Mat()
        val grayScape = Mat()
        val normalizeGrayScaleImage = Mat()

        Imgproc.cvtColor(input, grayScape, Imgproc.COLOR_BGR2GRAY)
        val clahe = Imgproc.createCLAHE(1.0, Size(8.0, 8.0))
        clahe.apply(grayScape, normalizeGrayScaleImage)
        Imgproc.threshold(normalizeGrayScaleImage, output, 128.0, 255.0, Imgproc.THRESH_OTSU)
        return output
    }

}