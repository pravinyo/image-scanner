package contrastenhancement

import ImageUtils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class AdaptiveHistogramEqualization(
    private val parameters: ClaheParameters
) : ContrastEnhancement {
    override fun execute(image: Mat): Mat {
        var output = Mat()
        val clahe = Imgproc.createCLAHE(parameters.clipLimit, parameters.tileGridSize)

        if (isColorImage(image.channels())) {
            val channelsAndyComponent = ImageUtils.getYComponentFromColorImage(image)
            val yEqualize = Mat()
            clahe.apply(channelsAndyComponent.second, yEqualize)
            output = ImageUtils.mergeYComponentReturnColorImage(
                channels = channelsAndyComponent.first.toMutableList(),
                yComponent = yEqualize
            )
        } else {
            clahe.apply(image, output)
        }

        return output
    }

    private fun isColorImage(channels: Int): Boolean {
        return channels > 1
    }
}

data class ClaheParameters(
    val clipLimit: Double = 40.0,
    val tileGridSize: Size = Size(8.0, 8.0)
)