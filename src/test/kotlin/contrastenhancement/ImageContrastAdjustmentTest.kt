package contrastenhancement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.MatOfDouble
import org.opencv.imgproc.Imgproc
import utility.ImageUtils

class ImageContrastAdjustmentTest {
     @BeforeEach
     fun setUp() {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
     }

     @Test
     fun `given grayscale image, it should be able to adjust contrast`() {
         val input = ImageUtils.loadImage("input/low_contrast.png")
         Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY)
         ImageUtils.saveImage("contrastenhancement/imadjust_1.jpg", input)

         val imageContrastAdjustment = ImageContrastAdjustment(ImageContrastAdjustParameters())

         val actual = imageContrastAdjustment.execute(input)

         assertEquals(input.size(), actual.size())
         assertEquals(input.channels(), actual.channels())
         assertEquals(CvType.CV_8UC1, actual.type())
         ImageUtils.saveImage("contrastenhancement/imadjust_2.jpg", actual)
     }

    @Test
    fun `given grayscale image, it should be able to adjust constrast based on mean and stddev`() {
        val input = ImageUtils.loadImage("input/low_contrast_gray.png")
        Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY)
        ImageUtils.saveImage("contrastenhancement/imadjust_1.jpeg", input)

        val meanMat = MatOfDouble()
        val stdDevMat = MatOfDouble()
        Core.meanStdDev(input, meanMat, stdDevMat)

        val mean = meanMat.get(0, 0)[0].toInt()
        val stdDev = stdDevMat.get(0, 0)[0].toInt()
        val n = 2

        val config = ImageContrastAdjustParameters(
            inputBound = Pair(mean - n * stdDev, mean + n * stdDev),
            outputBound = Pair(100, 250)
        )
        val imageContrastAdjustment = ImageContrastAdjustment(config)

        val actual = imageContrastAdjustment.execute(input)

        assertEquals(input.size(), actual.size())
        assertEquals(input.channels(), actual.channels())
        assertEquals(CvType.CV_8UC1, actual.type())
        ImageUtils.saveImage("contrastenhancement/imadjust_2.jpeg", actual)
    }

    @Test
    fun `given color image, it should be able to adjust contrast`() {
        val input = ImageUtils.loadImage("input/low_contrast.png")
        val configs = mutableListOf<ImageContrastAdjustParameters>()
        configs.add(ImageContrastAdjustParameters())
        configs.add(ImageContrastAdjustParameters(
            saturationPercentage = 2
        ))
        configs.add(ImageContrastAdjustParameters(
            saturationPercentage = 2,
            inputBound = Pair(50, 200)
        ))
        configs.add(ImageContrastAdjustParameters(
            saturationPercentage = 2,
            inputBound = Pair(70, 200)
        ))
        configs.add(ImageContrastAdjustParameters(
            saturationPercentage = 2,
            inputBound = Pair(70, 150)
        ))
        configs.add(ImageContrastAdjustParameters(
            saturationPercentage = 2,
            inputBound = Pair(70, 150),
            outputBound = Pair(50, 200)
        ))

        configs.forEachIndexed { index, config ->
            val imageContrastAdjustment = ImageContrastAdjustment(config)

            val actual = imageContrastAdjustment.execute(input)

            assertEquals(input.size(), actual.size())
            assertEquals(input.channels(), actual.channels())
            assertEquals(input.type(), actual.type())
            ImageUtils.saveImage("contrastenhancement/imadjust_$index.jpeg", actual)
        }
    }
 }