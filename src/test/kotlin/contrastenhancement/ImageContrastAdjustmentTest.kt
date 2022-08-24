package contrastenhancement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
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

         val imageContrastAdjustment = ImageContrastAdjustment(ImageContrastAdjustConfig())

         val actual = imageContrastAdjustment.execute(input)

         assertEquals(input.size(), actual.size())
         assertEquals(input.channels(), actual.channels())
         assertEquals(CvType.CV_8UC1, actual.type())
         ImageUtils.saveImage("contrastenhancement/imadjust_2.jpg", actual)
     }
 }