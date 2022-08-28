package filters

import filters.SmoothingFilterParameters.*
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class SmoothingFilter(private val parameters: SmoothingFilterParameters) : Filter {

    override fun convert(colorImage: Mat): Mat {
        return when (parameters) {
            is PartialImageDetails -> smoothPartialRegion(colorImage, parameters)
            is WholeImageDetails -> smoothWholeRegion(colorImage, parameters)
        }
    }

    private fun smoothPartialRegion(colorImage: Mat, parameters: PartialImageDetails): Mat {
        val image = colorImage.clone()
        Imgproc.GaussianBlur(
            image.submat(parameters.regionToSmooth),
            image.submat(parameters.regionToSmooth),
            Size(parameters.kernelSize.toDouble(), parameters.kernelSize.toDouble()),
            parameters.sigmaX,
            parameters.sigmaY,
            parameters.borderType
        )
        return image
    }

    private fun smoothWholeRegion(image: Mat, parameters: WholeImageDetails): Mat {
        val smoothImage = Mat()
        Imgproc.bilateralFilter(
            image,
            smoothImage,
            parameters.pixelNeighborDiameter,
            parameters.sigmaColor,
            parameters.sigmaSpace
        )
        return smoothImage
    }
}

const val DEFAULT = Int.MIN_VALUE
const val DEFAULT_BORDER_TYPE = Core.BORDER_DEFAULT

sealed class SmoothingFilterParameters {
    data class WholeImageDetails(
        val pixelNeighborDiameter: Int = DEFAULT,
        val sigmaColor: Double,
        val sigmaSpace: Double
    ) : SmoothingFilterParameters()

    data class PartialImageDetails(
        val regionToSmooth: Rect? = null,
        val kernelSize: Int,
        val sigmaX: Double,
        val sigmaY: Double = 0.0,
        val borderType: Int = DEFAULT_BORDER_TYPE
    ) : SmoothingFilterParameters() {
        init {
            require(kernelSize % 2 == 1) { " kernel size need to be odd value " }
        }
    }
}