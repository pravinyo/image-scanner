package contrastenhancement

import org.opencv.core.Size

data class ClaheConfig(
    val clipLimit: Double = 40.0,
    val tileGridSize: Size = Size(8.0,8.0)
)