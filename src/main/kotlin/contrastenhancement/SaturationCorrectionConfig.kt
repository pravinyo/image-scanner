package contrastenhancement

const val sameAsInputType = -1

data class SaturationCorrectionConfig(
    val alpha: Double,
    val beta: Double,
    val matrixOutputType: Int = sameAsInputType
)