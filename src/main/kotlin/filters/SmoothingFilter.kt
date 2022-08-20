package filters

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class SmoothingFilter : Filter {
    override fun convert(colorImage: Mat): Mat {
        val smoothImage = Mat()
        val equalizeImage = histogramEqualizationUsingYUV(colorImage)
        Imgproc.bilateralFilter(equalizeImage, smoothImage, 9, 75.0, 75.0)
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
}