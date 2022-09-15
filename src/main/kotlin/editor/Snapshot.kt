package editor

import utils.OperationType
import org.opencv.core.Mat

class Snapshot(
    private val operationList: List<OperationType>,
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