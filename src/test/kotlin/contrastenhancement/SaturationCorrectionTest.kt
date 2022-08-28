package contrastenhancement

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.imgproc.Imgproc
import utility.ImageUtils

internal class SaturationCorrectionTest {

    @BeforeEach
    fun setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @Test
    fun `given grayscale image, it should be able to saturate image`() {
        val input = ImageUtils.loadImage("input/dark_img.png")
        Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY)
        ImageUtils.saveImage("contrastenhancement/saturation_1.jpeg", input)
        val saturationCorrection = SaturationCorrection(SaturationCorrectionParameters(3.0, 10.0))

        val actual = saturationCorrection.execute(input)

        assertEquals(input.size(), actual.size())
        assertEquals(input.channels(), actual.channels())
        assertEquals(CvType.CV_8UC1, actual.type())
    }

    @Test
    fun `given color image, it should be able to saturate image`() {
        val input = ImageUtils.loadImage("input/dark_img.png")
        val saturationCorrection = SaturationCorrection(SaturationCorrectionParameters(3.0, 10.0))

        val actual = saturationCorrection.execute(input)

        assertEquals(input.size(), actual.size())
        assertEquals(input.channels(), actual.channels())
        assertEquals(input.type(), actual.type())
        ImageUtils.saveImage("contrastenhancement/saturation_2.jpeg", actual)
    }
}