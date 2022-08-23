package contrastenhancement

import org.opencv.core.Mat

class SaturationCorrection(
    private val config: SaturationCorrectionConfig
) : ContractEnhancement {

    override fun execute(image: Mat): Mat {
        val outputImage = Mat()
        image.convertTo(outputImage, config.matrixOutputType, config.alpha, config.beta)
        return outputImage
    }
}