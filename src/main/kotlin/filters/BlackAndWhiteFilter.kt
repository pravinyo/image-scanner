package filters

import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class BlackAndWhiteFilter : Filter {

    override fun convert(colorImage: Mat): Mat {
        val output = Mat()

        val grayScape = convertToGrayscale(colorImage)
        val normalizeGrayScaleImage = adaptiveHistogramEqualization(grayScape)
        Imgproc.threshold(normalizeGrayScaleImage, output, 128.0, 255.0, Imgproc.THRESH_OTSU)

        return output
    }

    private fun convertToGrayscale(input: Mat): Mat {
        val grayScape = Mat()
        Imgproc.cvtColor(input, grayScape, Imgproc.COLOR_BGR2GRAY)
        return grayScape
    }

    private fun adaptiveHistogramEqualization(grayScape: Mat): Mat{
        val normalizeGrayScaleImage = Mat()
        val clahe = Imgproc.createCLAHE(2.0, Size(8.0, 8.0))
        clahe.apply(grayScape, normalizeGrayScaleImage)
        return normalizeGrayScaleImage
    }

}