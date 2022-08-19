package filters

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class SharpeningFilter : Filter {

     override fun convert(colorImage: Mat): Mat {
        val yuvImage = Mat()
        Imgproc.cvtColor(colorImage, yuvImage, Imgproc.COLOR_BGR2YUV)

        val channels = mutableListOf<Mat>()
        Core.split(yuvImage, channels)
        val yChannel = channels[0]
        val ySharp = performSharpening(yChannel)

        val output = Mat()
        channels.removeAt(0)
        channels.add(0, ySharp)
        Core.merge(channels, output)
        Imgproc.cvtColor(output, output, Imgproc.COLOR_YUV2BGR)
        return output
    }

   private fun performSharpening( image: Mat): Mat {
      val blurImage = Mat()
      val kSize = 3.0
      val sigma = 0.0
      Imgproc.GaussianBlur(image, blurImage, Size(kSize,kSize), sigma, sigma, Core.BORDER_DEFAULT)

      val noisyLayer = Mat()
      Imgproc.Laplacian(blurImage, noisyLayer, CvType.CV_8U, 3)

      val sharpImage = Mat(blurImage.size(), blurImage.type())
      Core.subtract(blurImage, noisyLayer, sharpImage)

      return sharpImage
   }
}