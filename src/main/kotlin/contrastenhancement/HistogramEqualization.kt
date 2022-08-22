package contrastenhancement

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class HistogramEqualization : ContractEnhancement {
    override fun execute(image: Mat): Mat {
        val output = Mat()

        if (isColorImage(image.channels())) {
            val yuvImage = Mat()
            Imgproc.cvtColor(image, yuvImage, Imgproc.COLOR_BGR2YUV)
            val channels = mutableListOf<Mat>()
            Core.split(yuvImage, channels)

            val yComponent = channels[0]
            val yEqualize = Mat()
            Imgproc.equalizeHist(yComponent, yEqualize)
            channels.removeAt(0)
            channels.add(0, yEqualize)

            Core.merge(channels, output)
            Imgproc.cvtColor(output, output, Imgproc.COLOR_YUV2BGR)
        } else {
            Imgproc.equalizeHist(image, output)
        }
        return output
    }

    private fun isColorImage(channels: Int): Boolean {
        return channels == 3
    }
}