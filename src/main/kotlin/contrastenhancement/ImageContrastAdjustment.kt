package contrastenhancement

import org.opencv.core.Mat
import kotlin.math.max
import kotlin.math.min

class ImageContrastAdjustment(
    val config: ImageContrastAdjustConfig
) : ContractEnhancement {
    override fun execute(image: Mat): Mat {
        checkImageChannels(image)
        var input = image.clone()
        var channelsAndyComponent: Pair<List<Mat>, Mat> = Pair(emptyList(), Mat())

        if(isColorImage(image)) {
            channelsAndyComponent = ImageUtils.getYComponentFromColorImage(image)
            input = channelsAndyComponent.second
        }
        val cumulativeHistogram: IntArray = generateCumulativeHistogram(input)
        val inputBound: Pair<Int, Int> = getInputBound(input, cumulativeHistogram)

        //stretching
        val scale = (config.outputBound.second - config.outputBound.first)
            .toFloat().div(inputBound.second - inputBound.first)

        //transformation
        var output = Mat(input.size(), input.type())
        for (row in 0 until input.rows()) {
            for (col in 0 until input.cols()) {
                val intensityValue = input.get(row, col)[0].toInt()

                val transformedValue = linearTransformation(
                    intensityValue = intensityValue,
                    lowerBound = inputBound.first,
                    scale = scale
                )

                output.put(row, col, transformedValue.toDouble())
            }
        }

        if(isColorImage(image)) {
            output = ImageUtils.mergeYComponentReturnColorImage(channelsAndyComponent.first.toMutableList(),output)
        }

        return output
    }

    private fun isColorImage(image: Mat): Boolean {
        return image.channels() == 3
    }

    private fun checkImageChannels(image: Mat) {
        if (image.channels() != 1 && image.channels() != 3) {
            error("Channel should be 1 or 3 for image")
        }
    }

    private fun linearTransformation(
        intensityValue: Int,
        lowerBound: Int,
        scale: Float,
    ): Int {
        val outputBound = config.outputBound
        val difference = max(0, intensityValue - lowerBound)
        val transformedValue =
            min((difference.times(scale) + 0.5f).toInt() + outputBound.first, outputBound.second)
        return transformedValue
    }

    private fun getInputBound(image: Mat, cumulativeHistogram: IntArray): Pair<Int, Int> {
        val total = image.rows() * image.cols()
        val lowerPercentile = total * config.saturationPercentage / 100
        val upperPercentile = total * (100 - config.saturationPercentage) / 100

        val lowerBoundIntensityValue = cumulativeHistogram.binarySearch(lowerPercentile)
        val upperBoundIntensityValue = cumulativeHistogram.binarySearch(upperPercentile)

        val inputLowerBoundFinal =
            if (lowerBoundIntensityValue < 0) -lowerBoundIntensityValue else lowerBoundIntensityValue
        val inputUpperBoundFinal =
            if (upperBoundIntensityValue < 0) -upperBoundIntensityValue else upperBoundIntensityValue

        return Pair(inputLowerBoundFinal, inputUpperBoundFinal)
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