package contrastenhancement

import org.opencv.core.Mat
import kotlin.math.max
import kotlin.math.min

class ImageContrastAdjustment : ContractEnhancement {
    override fun execute(image: Mat): Mat {
        if (image.channels() != 1) error("Channel should be 1 for image")

        val saturation = 1
        val outputBound = Pair(0, 255)
        val cumulativeHistogram: IntArray = generateCumulativeHistogram(image)

        val total = image.rows() * image.cols()
        val lowerPercentile = total * saturation / 100
        val upperPercentile = total * (100 - saturation) / 100

        val lowerBoundIntensityValue = cumulativeHistogram.binarySearch(lowerPercentile)
        val upperBoundIntensityValue = cumulativeHistogram.binarySearch(upperPercentile)

        val inputLowerBoundFinal = if (lowerBoundIntensityValue < 0) -lowerBoundIntensityValue else lowerBoundIntensityValue
        val inputUpperBoundFinal = if (upperBoundIntensityValue < 0) -upperBoundIntensityValue else upperBoundIntensityValue

        //stretching
        val scale = (outputBound.second - outputBound.first).toFloat() / (inputUpperBoundFinal - inputLowerBoundFinal)

        //transformation
        val output = Mat(image.size(), image.type())
        for (row in 0 until image.rows()) {
            for (col in 0 until image.cols()) {
                val intensityValue = image.get(row, col)[0].toInt()
                val difference = max(0, intensityValue - inputLowerBoundFinal)
                val transformedValue = min((difference.times(scale) + 0.5f).toInt() + outputBound.first, outputBound.second)
                output.put(row, col, transformedValue.toDouble())
            }
        }

        return output
    }

    private fun generateCumulativeHistogram(image: Mat): IntArray {
        val histogram = IntArray(256) { 0 }
        for (row in 0 until image.rows()) {
            for (col in 0 until image.cols()) {
                val intensityValue = image.get(row, col)[0].toInt()
                histogram[intensityValue]++
            }
        }

        val cumulativeHistogram = histogram.clone()
        for (index in 1 until histogram.size) {
            cumulativeHistogram[index] = cumulativeHistogram[index] + cumulativeHistogram[index - 1]
        }

        return cumulativeHistogram
    }
}