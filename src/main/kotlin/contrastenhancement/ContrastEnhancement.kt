package contrastenhancement

import org.opencv.core.Mat

interface ContrastEnhancement {
    fun execute(image: Mat): Mat
}