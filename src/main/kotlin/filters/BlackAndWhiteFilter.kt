package filters

import utils.OperationType
import contrastenhancement.ClaheParameters
import factory.ContrastEnhancementFactory
import factory.FilterFactory
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class BlackAndWhiteFilter(
    private val filterFactory: FilterFactory,
    private val contrastEnhancementFactory: ContrastEnhancementFactory,
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
        val filter = filterFactory.createInstance(OperationType.GrayscaleFilter)
        return filter.convert(input)
    }

    private fun adjustContrast(grayImage: Mat): Mat {
        val claheParameters = ClaheParameters(clipLimit = 2.0)
        val histogramOperationType = OperationType.AdaptiveHistogramEnhancement(claheParameters)
        val contrastAdjustment = contrastEnhancementFactory.createInstance(histogramOperationType)
        return contrastAdjustment.execute(grayImage)
    }
}

data class BlackAndWhiteFilterParameters(
    val threshold: Double = 128.0,
    val maxIntensityValue: Double = 255.0
)

