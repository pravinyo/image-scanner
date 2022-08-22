package contrastenhancement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.imgproc.Imgproc
import utility.ImageUtils

class GammaCorrectionTest {
     @BeforeEach
     fun setUp() {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
     }

     @Test
     fun `given grayscale image, it should be able to enhance dark pixels`() {
         val input = ImageUtils.loadImage("input/sample.jpeg")
         Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY)
         ImageUtils.saveImage("contrastenhancement/gamma_0.jpeg", input)
         val gammaCorrection = GammaCorrection(0.5)

         val actual = gammaCorrection.execute(input)

         assertEquals(input.size(), actual.size())
         assertEquals(input.channels(), actual.channels())
         assertEquals(CvType.CV_8UC1, actual.type())
         ImageUtils.saveImage("contrastenhancement/gamma_2.jpeg", actual)
     }
 }