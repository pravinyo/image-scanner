package transformations

import org.opencv.core.Mat

interface Transformation {
    fun execute(colorImage: Mat) : Mat
}