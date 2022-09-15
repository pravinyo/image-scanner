package contrastenhancement


import utils.ImageUtils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class HistogramEqualization : ContrastEnhancement {
    override fun execute(image: Mat): Mat {
        var output = Mat()

        if (isColorImage(image.channels())) {
            val channelsAndyComponent = ImageUtils.getYComponentFromColorImage(image)
            val yEqualize = Mat()

            Imgproc.equalizeHist(channelsAndyComponent.second, yEqualize)
            output = ImageUtils.mergeYComponentReturnColorImage(
                channels = channelsAndyComponent.first.toMutableList(),
                yComponent = yEqualize
            )
        } else {
            Imgproc.equalizeHist(image, output)
        }
        return output
    }

    private fun isColorImage(channels: Int): Boolean {
        return channels == 3
    }
}