package contrastenhancement

import org.opencv.core.Mat

class SaturationCorrection(
    private val parameters: SaturationCorrectionParameters
) : ContrastEnhancement {

    override fun execute(image: Mat): Mat {
        val outputImage = Mat()
        image.convertTo(outputImage, parameters.matrixOutputType, parameters.alpha, parameters.beta)
        return outputImage
    }
}

const val sameAsInputType = -1
data class SaturationCorrectionParameters(
    val alpha: Double,
    val beta: Double,
    val matrixOutputType: Int = sameAsInputType
)