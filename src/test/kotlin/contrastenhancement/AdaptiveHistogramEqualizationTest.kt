package contrastenhancement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.imgproc.Imgproc
import utility.ImageUtils

class AdaptiveHistogramEqualizationTest {
     @BeforeEach
     fun setUp() {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
     }

     @Test
     fun `given grayscale image, it should be able to equalize intensities`() {
         val input = ImageUtils.loadImage("input/sample.jpeg")
         Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY)
         ImageUtils.saveImage("contrastenhancement/histogram_0.jpeg", input)
         val histogramEqualization = AdaptiveHistogramEqualization(ClaheConfig(clipLimit = 2.0))

         val actual = histogramEqualization.execute(input)

         assertEquals(input.size(), actual.size())
         assertEquals(input.channels(), actual.channels())
         assertEquals(CvType.CV_8UC1, actual.type())
         ImageUtils.saveImage("contrastenhancement/histogram_1.jpeg", actual)
     }

    @Test
    fun `given color image, it should be able to equalize intensities`() {
        val input = ImageUtils.loadImage("input/sample.jpeg")
        val histogramEqualization = AdaptiveHistogramEqualization(ClaheConfig(clipLimit = 1.0))

        val actual = histogramEqualization.execute(input)

        assertEquals(input.size(), actual.size())
        assertEquals(input.channels(), actual.channels())
        assertEquals(input.type(), actual.type())
        ImageUtils.saveImage("contrastenhancement/histogram_clahe_2.jpeg", actual)
    }
 }