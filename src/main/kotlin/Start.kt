import org.opencv.core.Core
import org.opencv.imgcodecs.Imgcodecs.imread

object Start {

    @JvmStatic
    fun main(args: Array<String>) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val m = imread("C:/Users/tripa/Downloads/image-scanner/src/main/resources/images/sample.png")
        println(m.rows().toString()+", "+m.cols())
    }
}