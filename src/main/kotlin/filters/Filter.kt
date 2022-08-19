package filters

import org.opencv.core.Mat

interface Filter {
    fun convert(colorImage: Mat) : Mat
}