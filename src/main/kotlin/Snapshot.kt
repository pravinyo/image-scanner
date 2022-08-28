import org.opencv.core.Mat

class Snapshot(
    private val operationList: List<String>,
    private val activeImage: Mat,
    private val imageEditor: ImageEditor
) {

    fun getOperations() = operationList

    fun activeImage() = activeImage

    fun restore() {
        imageEditor.run{
            setActiveImage(activeImage)
            resetOperationList(operationList)
        }
    }
}