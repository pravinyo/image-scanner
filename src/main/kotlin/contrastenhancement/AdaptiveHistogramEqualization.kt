package contrastenhancement

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class AdaptiveHistogramEqualization(
    private val config: ClaheConfig
) : ContractEnhancement {
    override fun execute(image: Mat): Mat {
        var output = Mat()
        val clahe = Imgproc.createCLAHE(config.clipLimit, config.tileGridSize)

        if (isColorImage(image.channels())) {
            val channelsAndyComponent = ImageUtils.getYComponentFromColorImage(image)
            val yEqualize = Mat()
            clahe.apply(channelsAndyComponent.second, yEqualize)
            output = ImageUtils.mergeYComponentReturnColorImage(channelsAndyComponent.first.toMutableList(), yEqualize)
        } else {
            clahe.apply(image, output)
        }

        return output
    }

    private fun isColorImage(channels: Int): Boolean {
        return channels > 1
    }
}