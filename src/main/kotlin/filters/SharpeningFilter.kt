package filters

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class SharpeningFilter : Filter {

     override fun convert(colorImage: Mat): Mat {
        val blurImage = Mat()
        val kSize = 3.0
        val sigma = 0.0
        Imgproc.GaussianBlur(colorImage, blurImage, Size(kSize,kSize), sigma, sigma, Core.BORDER_DEFAULT)

        val noisyLayer = Mat()
        Imgproc.Laplacian(colorImage, noisyLayer, CvType.CV_8U, 3)

        val output = Mat(colorImage.size(), colorImage.type())
        Core.subtract(colorImage, noisyLayer, output)
        return output
    }
}