package draw

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import transformations.RotationTransformation
import transformations.RotationTransformationConfig.ArbitraryDirectionConfig

class Text(
    private val parameters: TextParameters
) {
    fun addTo(image: Mat): Mat {
        val output = image.clone()
        val textSize = getTextSize()
        val bestPosition = findBestPositionOnImage(textSize, image.size())
        addTextTo(output, bestPosition)
        return output
    }

    fun addRectangleAroundText(image: Mat): Mat {
        val output = image.clone()
        val textSize = getTextSize()
        val bestPosition = findBestPositionOnImage(textSize, image.size())

        addRectangle(output, bestPosition, textSize)
        addTextTo(output, bestPosition)
        return output
    }

    fun addRotatedTextWithRectangle(image: Mat, angle: Double): Mat {
        val intermediateImage = Mat.zeros(image.size(), image.type())
        val textSize = getTextSize()
        val bestPosition = findBestPositionOnImage(textSize, image.size())

        addRectangle(intermediateImage, bestPosition, textSize)
        addTextTo(intermediateImage, bestPosition)

        val rotationConfig = ArbitraryDirectionConfig(
            angle = angle,
            center = Point(bestPosition.x + textSize.width / 2, bestPosition.y + textSize.height / 2)
        )
        val rotatedTextImage = RotationTransformation(rotationConfig).execute(intermediateImage)

        return image.copyAndOverrideWith(rotatedTextImage)
    }

    private fun Mat.copyAndOverrideWith(other: Mat): Mat {
        assert(this.size() == other.size())
        assert(this.channels() == other.channels())
        assert(this.type() == other.type())
        val output = this.clone()

        for (row in 0 until this.rows()) {
            for (col in 0 until this.cols()) {
                val count = other.get(row, col).sum()
                if (count > 0) {
                    output.put(row, col, *other.get(row, col))
                }
            }
        }

        return output
    }

    private fun getTextSize(): Size {
        return Imgproc.getTextSize(
            parameters.text,
            parameters.fontFace,
            parameters.fontScale,
            parameters.thickness,
            intArrayOf(parameters.baseLine)
        )
    }

    private fun findBestPositionOnImage(textSize: Size, imageSize: Size): Point {
        val position = parameters.bottomLeftCorner
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

    private fun addRectangle(image: Mat, bestPosition: Point, textSize: Size) {
        val baseLine = parameters.baseLine + parameters.thickness

        Imgproc.rectangle(
            image,
            Point(bestPosition.x, bestPosition.y + baseLine),
            Point(bestPosition.x + textSize.width, bestPosition.y - textSize.height),
            Scalar(0.0, 0.0, 255.0)
        )
    }

    private fun addTextTo(image: Mat, position: Point) {
        Imgproc.putText(
            image,
            parameters.text,
            position,
            parameters.fontFace,
            parameters.fontScale,
            parameters.color,
            parameters.thickness,
            parameters.lineType,
            parameters.bottomLeftOrigin
        )
    }

}