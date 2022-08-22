package transformations

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.utils.Converters

class PerspectiveTransformation(
    private val sourcePoints: List<Point>,
    private val destPoints: List<Point>
) : Transformation {
    override fun execute(colorImage: Mat): Mat {
        val sourceMatrix = Converters.vector_Point2f_to_Mat(sourcePoints)
        val destMatrix = Converters.vector_Point2f_to_Mat(destPoints)
        val transformationMatrix = Imgproc.getPerspectiveTransform(sourceMatrix, destMatrix)

        val size = Size(300.0, 300.0)
        val output = Mat(size, colorImage.type())
        Imgproc.warpPerspective(colorImage, output, transformationMatrix, size)

        return output
    }
}