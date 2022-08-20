package utility

import org.opencv.core.Core
import org.opencv.core.Mat

object AssertionsUtil {
    fun areEqual(image1: Mat, image2: Mat): Boolean {
        val temp = Mat()
        Core.bitwise_xor(image1, image2, temp)
        return Core.countNonZero(temp.reshape(1)) == 0
    }
}