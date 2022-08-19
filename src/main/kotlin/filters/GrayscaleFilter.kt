package filters

import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class GrayscaleFilter : Filter {

    override fun convert(colorImage: Mat): Mat {
        val output = Mat()
        Imgproc.cvtColor(colorImage, output, Imgproc.COLOR_BGR2GRAY)
        return output
    }
}