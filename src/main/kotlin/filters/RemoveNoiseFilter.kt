package filters

import org.opencv.core.Mat
import org.opencv.photo.Photo

class RemoveNoiseFilter: Filter {
    override fun convert(colorImage: Mat): Mat {
        val output = Mat()
        Photo.fastNlMeansDenoisingColored(colorImage, output, 10f, 10f, 7, 21)
        return output
    }
}