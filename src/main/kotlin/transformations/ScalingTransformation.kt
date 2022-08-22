package transformations

import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ScalingTransformation : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val newImage = Mat()
        val newImageSize = Size(colorImage.size().width * 2,colorImage.size().height * 2 )
        Imgproc.resize(colorImage, newImage, newImageSize, 0.0, 0.0, Imgproc.INTER_CUBIC)

        return newImage
    }
}