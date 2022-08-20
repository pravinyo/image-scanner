package filters

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class UnsharpMaskBoostFilter(private var config: UnsharpMaskConfig) : Filter {

    override fun convert(colorImage: Mat): Mat {
        val yuvImage = Mat()
        Imgproc.cvtColor(colorImage, yuvImage, Imgproc.COLOR_BGR2YUV)

        val channels = mutableListOf<Mat>()
        Core.split(yuvImage, channels)

        val yChannel = channels[0]
        val blurYChannel = createBlur(yChannel)
        val mask = createMask(yChannel, blurYChannel)
        val boostMask = boostMask(mask, config.boostAmount)

        val yChannelFinal = Mat()
        Core.add(yChannel, boostMask, yChannelFinal)

        val output = merge(channels, yChannelFinal)
        Imgproc.cvtColor(output, output, Imgproc.COLOR_YUV2BGR)
        return output
    }

    private fun createBlur(image: Mat): Mat {
        val output = Mat()
        val kSize = config.kernelSize
        val sigma = config.sigma
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

    private fun merge(channels: MutableList<Mat>, yChannel: Mat): Mat {
        channels.removeAt(0)
        channels.add(0, yChannel)

        val output = Mat()
        Core.merge(channels, output)
        return output
    }
}

data class UnsharpMaskConfig(
    val kernelSize: Double,
    val sigma: Double,
    val boostAmount: Double
)