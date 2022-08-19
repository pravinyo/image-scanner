package filters

import org.opencv.core.Mat
import org.opencv.photo.Photo

class RemoveNoiseFilter: Filter {
    override fun convert(colorImage: Mat): Mat {
        val output = Mat()
        Photo.fastNlMeansDenoising(colorImage, output, 30f, 7, 21)
        return output
    }
}