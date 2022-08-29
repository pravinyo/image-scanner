package contrastenhancement

import org.opencv.core.Mat
import kotlin.math.pow

class GammaCorrection(private val gamma: Double) : ContrastEnhancement{
    override fun execute(image: Mat): Mat {
        val transform = DoubleArray(256) {
            index -> (index/255.0).pow(gamma) * 255.0
        }

        val output = Mat(image.size(), image.type())

        for(row in 0 until image.rows()) {
            for(column in 0 until image.cols()) {
                val newValue: List<Double> = image.get(row, column).map{ oldValue ->
                    transform[oldValue.toInt()]
                }

                output.put(row, column, *newValue.toDoubleArray())
            }
        }

        return output
    }
}