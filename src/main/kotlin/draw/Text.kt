package draw

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Text(
    private val parameters: TextParameters
) {
    fun addTo(image: Mat): Mat {
        val output = image.clone()
        val textSize = Imgproc.getTextSize(
            parameters.text,
            parameters.fontFace,
            parameters.fontScale,
            parameters.thickness,
            intArrayOf(parameters.baseLine)
        )
        val bestPosition = findBestPositionOnImage(textSize, parameters.bottomLeftCorner, image.size())

        Imgproc.putText(
            output,
            parameters.text,
            bestPosition,
            parameters.fontFace,
            parameters.fontScale,
            parameters.color,
            parameters.thickness,
            parameters.lineType,
            parameters.bottomLeftOrigin
        )
        return output
    }

    private fun findBestPositionOnImage(textSize: Size, position: Point, imageSize: Size): Point {

        var x = position.x
        var y = position.y

        if (position.x + textSize.width > imageSize.width) {
            x = imageSize.width - textSize.width
        }

        if (position.y + textSize.height / 2 > imageSize.height) {
            y = imageSize.height - textSize.height / 2
        }

        if (position.y < textSize.height) {
            y = textSize.height
        }

        if (position.x < 0) {
            x = 0.0
        }

        return Point(x, y)
    }

    fun addLogo(image: Mat): Mat {
        val output = image.clone()
        val textSize = Imgproc.getTextSize(
            parameters.text,
            parameters.fontFace,
            parameters.fontScale,
            parameters.thickness,
            intArrayOf(parameters.baseLine)
        )
        val baseLine = parameters.baseLine + parameters.thickness

        val bestPosition = findBestPositionOnImage(textSize, parameters.bottomLeftCorner, image.size())

        Imgproc.rectangle(
            output,
            Point(bestPosition.x, bestPosition.y + baseLine),
            Point(bestPosition.x + textSize.width, bestPosition.y  - textSize.height),
            Scalar(0.0, 0.0, 255.0)
        )

        Imgproc.putText(
            output,
            parameters.text,
            bestPosition,
            parameters.fontFace,
            parameters.fontScale,
            parameters.color,
            parameters.thickness,
            parameters.lineType,
            parameters.bottomLeftOrigin
        )

        return output
    }

}