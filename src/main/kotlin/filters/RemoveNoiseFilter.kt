package filters

import org.opencv.core.Mat
import org.opencv.photo.Photo

class RemoveNoiseFilter(
    private val parameters: RemoveNoiseFilterParameters
) : Filter {
    override fun convert(colorImage: Mat): Mat {
        val output = Mat()
        Photo.fastNlMeansDenoisingColored(
            colorImage,
            output,
            parameters.filterStrengthOnLuminance,
            parameters.filterStrengthOnColorComponent,
            parameters.templateWindowSize,
            parameters.searchWindowSize
        )
        return output
    }
}

data class RemoveNoiseFilterParameters(
    val filterStrengthOnLuminance: Float = 3f,
    val filterStrengthOnColorComponent: Float = 3f,
    val templateWindowSize: Int = 7,
    val searchWindowSize: Int = 21
)