package contrastenhancement

data class ImageContrastAdjustParameters(
    val saturationPercentage: Int = 1,
    val inputBound: Pair<Int, Int>? = null,
    val outputBound: Pair<Int, Int> = Pair(0, 255)
)