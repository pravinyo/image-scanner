import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

object ImageUtils {

    fun getYComponentFromColorImage(image: Mat): Pair<List<Mat>, Mat> {
        val yuvImage = Mat()
        Imgproc.cvtColor(image, yuvImage, Imgproc.COLOR_BGR2YUV)
        val channels = mutableListOf<Mat>()
        Core.split(yuvImage, channels)

        return Pair(channels, channels[0])
    }

    fun mergeYComponentReturnColorImage(channels: MutableList<Mat>, yComponent: Mat) : Mat {
        val output = Mat()
        channels.removeAt(0)
        channels.add(0, yComponent)

        Core.merge(channels, output)
        Imgproc.cvtColor(output, output, Imgproc.COLOR_YUV2BGR)

        return output
    }
}