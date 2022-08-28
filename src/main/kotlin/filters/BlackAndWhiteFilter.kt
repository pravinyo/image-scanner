package filters

import contrastenhancement.AdaptiveHistogramEqualization
import contrastenhancement.ClaheParameters
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class BlackAndWhiteFilter(
    private val parameters: BlackAndWhiteFilterParameters
) : Filter {

    override fun convert(colorImage: Mat): Mat {
        val output = Mat()
        val grayImage = convertToGrayscale(colorImage)
        val normalizeGrayScaleImage = adjustContrast(grayImage)
        Imgproc.threshold(
            normalizeGrayScaleImage,
            output,
            parameters.threshold,
            parameters.maxIntensityValue,
            Imgproc.THRESH_OTSU
        )
        return output
    }

    private fun convertToGrayscale(input: Mat): Mat {
        val filter = GrayscaleFilter() //TODO: smell
        return filter.convert(input)
    }

    private fun adjustContrast(grayImage: Mat): Mat {
        val claheParameters = ClaheParameters(clipLimit = 2.0)
        val contrastAdjustment = AdaptiveHistogramEqualization(claheParameters) //TODO: Smell
        return contrastAdjustment.execute(grayImage)
    }
}

data class BlackAndWhiteFilterParameters(
    val threshold: Double = 128.0,
    val maxIntensityValue: Double = 255.0
)

