package filters

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class SmoothingFilter : Filter {
    var regionToSmooth: Rect? = null

    override fun convert(colorImage: Mat): Mat {
        return if (regionToSmooth == null) {
            smoothWholeRegion(colorImage)
        } else {
            smoothPartialRegion(colorImage)
        }
    }

    private fun smoothPartialRegion(colorImage: Mat): Mat {
        val image = colorImage.clone()
        Imgproc.GaussianBlur(
            image.submat(regionToSmooth),
            image.submat(regionToSmooth),
            Size(21.0, 21.0),
            10.0,
            10.0,
            Core.BORDER_DEFAULT
        )
        return image
    }

    private fun smoothWholeRegion(image: Mat): Mat {
        val smoothImage = Mat()
        Imgproc.bilateralFilter(image, smoothImage, 9, 75.0, 75.0)
        return smoothImage
    }

    private fun histogramEqualizationUsingYUV(image: Mat) : Mat {
        val yuv = Mat()
        Imgproc.cvtColor(image, yuv, Imgproc.COLOR_BGR2YUV)
        val channels = mutableListOf<Mat>()
        Core.split(yuv, channels)

        val yEqualize = Mat()
        Imgproc.equalizeHist(channels[0], yEqualize)
        channels.removeAt(0)
        channels.add(0, yEqualize)

        val output = Mat()
        Core.merge(channels, output)
        Imgproc.cvtColor(output, output, Imgproc.COLOR_YUV2BGR)

        return output
    }

    fun setRegion(regionToSmooth: Rect) {
        this.regionToSmooth = regionToSmooth
    }
}