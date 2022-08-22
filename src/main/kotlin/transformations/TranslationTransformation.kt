package transformations

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class TranslationTransformation(
    private val config: TranslationTransformationConfig
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val shiftedImage = Mat(colorImage.size(), colorImage.type())
        val transformationMatrix = Mat(2, 3, CvType.CV_64FC1)
        val col = 0
        val row = 0
        transformationMatrix.put(row, col, 1.0, 0.0, config.shiftInXDirection, 0.0, 1.0, config.shiftInYDirection)
        Imgproc.warpAffine(colorImage, shiftedImage, transformationMatrix, shiftedImage.size())
        return shiftedImage
    }

}