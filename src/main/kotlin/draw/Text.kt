package draw

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Text(
    private val parameters: TextParameters
) {
    fun addTo(image: Mat): Mat {
        val output = image.clone()

        val text = parameters.text
        val fontFace = parameters.fontFace
        val fontScale = parameters.fontScale
        val color = parameters.color
        val thickness = 3
        val baseLine = 0

        val textSize = Imgproc.getTextSize(text, fontFace, fontScale, thickness, intArrayOf(baseLine))
        val bestPosition = findBestPositionOnImage(textSize, parameters.bottomLeftCorner, image.size())

        Imgproc.putText(output, text, bestPosition, fontFace, fontScale, color, thickness)

        return output
    }

    private fun findBestPositionOnImage(textSize: Size, position: Point, imageSize:Size) : Point {

        var x = position.x
        var y = position.y

        if(position.x + textSize.width > imageSize.width) {
            x = imageSize.width - textSize.width
        }

        if (position.y + textSize.height / 2 > imageSize.height) {
            y = imageSize.height - textSize.height / 2
        }

        if (position.y < textSize.height) {
            y = textSize.height
        }

        if(position.x < 0) {
            x = 0.0
        }

        return Point(x, y)
    }

}