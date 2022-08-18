package filters

import org.opencv.core.Core
import org.opencv.core.Mat

class NegativeFilter {
    fun convert(input: Mat): Mat {
        val output = Mat()
        Core.bitwise_not(input, output)
        return output
    }
}