import org.opencv.core.Mat
import java.util.*

class StateManager {
    private lateinit var activeImage : Mat
    private lateinit var operations: Stack<String>

    fun initialize(image: Mat) {
        activeImage = image.clone()
        operations = Stack()
    }

    fun setActiveImage(image: Mat) {
        activeImage = image
    }

    fun getActiveImage(): Mat {
        return activeImage.clone()
    }

    fun getOperationsInfo(): List<String> {
        return operations.toList()
    }

    fun appendOperationInfo(operationType: String) {
        operations.push(operationType)
    }

    fun resetOperationsInfo(operationList: List<String>) {
        operations.clear()
        operations.addAll(operationList)
    }
}