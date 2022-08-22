package contrastenhancement

import org.opencv.core.Mat

interface ContractEnhancement {
    fun execute(image: Mat): Mat
}