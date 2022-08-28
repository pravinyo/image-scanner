package filters

import ImageUtils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class UnsharpMaskBoostFilter(private var parameters: UnsharpMaskParameters) : Filter {

    override fun convert(colorImage: Mat): Mat {
        val channelsAndyComponent = ImageUtils.getYComponentFromColorImage(colorImage)
        val yChannel = channelsAndyComponent.second

        val blurYChannel = createBlur(yChannel)
        val mask = createMask(yChannel, blurYChannel)
        val boostMask = boostMask(mask, parameters.boostAmount)

        val yChannelFinal = Mat()
        Core.add(yChannel, boostMask, yChannelFinal)

        return ImageUtils.mergeYComponentReturnColorImage(
            channels = channelsAndyComponent.first.toMutableList(),
            yComponent = yChannelFinal
        )
    }

    private fun createBlur(image: Mat): Mat {
        val output = Mat()
        val kSize = parameters.kernelSize
        val sigma = parameters.sigma
        Imgproc.GaussianBlur(image, output, Size(kSize, kSize), sigma, sigma, Core.BORDER_DEFAULT)
        return output
    }

    private fun createMask(image: Mat, blurImage: Mat): Mat {
        val mask = Mat()
        Core.subtract(image, blurImage, mask)
        return mask
    }

    private fun boostMask(mask: Mat, amount: Double): Mat {
        val times = Scalar(amount)
        val output = Mat()
        Core.multiply(mask, times, output)
        return output
    }
}

data class UnsharpMaskParameters(
    val kernelSize: Double,
    val sigma: Double,
    val boostAmount: Double
)