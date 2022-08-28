package filters

import ImageUtils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class SharpeningFilter(
    private val parameters: SharpeningFilterParameters
) : Filter {

    override fun convert(colorImage: Mat): Mat {
        val channelsAndyComponent = ImageUtils.getYComponentFromColorImage(colorImage)
        val yChannel = channelsAndyComponent.second

        val ySharp = performSharpening(yChannel)

        return ImageUtils.mergeYComponentReturnColorImage(
            channels = channelsAndyComponent.first.toMutableList(),
            yComponent = ySharp
        )
    }

    private fun performSharpening(image: Mat): Mat {
        val blurImage = Mat()
        val kSize = parameters.blurKernelSize.toDouble()
        val sigma = parameters.sigma
        Imgproc.GaussianBlur(image, blurImage, Size(kSize, kSize), sigma, sigma, Core.BORDER_DEFAULT)

        val noisyLayer = Mat()
        Imgproc.Laplacian(blurImage, noisyLayer, CvType.CV_8U, parameters.noiseKernelSize)

        val sharpImage = Mat(blurImage.size(), blurImage.type())
        Core.subtract(blurImage, noisyLayer, sharpImage)

        return sharpImage
    }
}

data class SharpeningFilterParameters(
    val blurKernelSize: Int = 3,
    val noiseKernelSize: Int = 3,
    val sigma: Double = 0.0,
    val borderType: Int = DEFAULT_BORDER_TYPE
) {
    init {
        require(blurKernelSize % 2 == 1) { " kernel size need to be odd value " }
    }
}