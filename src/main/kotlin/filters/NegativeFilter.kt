package filters

import org.opencv.core.Core
import org.opencv.core.Mat

class NegativeFilter : Filter {
    override fun convert(colorImage: Mat): Mat {
        val output = Mat()
        Core.bitwise_not(colorImage, output)
        return output
    }
}