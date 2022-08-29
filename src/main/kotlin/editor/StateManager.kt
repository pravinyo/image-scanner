package editor

import OperationType
import org.opencv.core.Mat
import java.util.*

class StateManager {
    private lateinit var activeImage : Mat
    private lateinit var operations: Stack<OperationType>

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

    fun getOperationsInfo(): List<OperationType> {
        return operations.toList()
    }

    fun appendOperationInfo(operationType: OperationType) {
        operations.push(operationType)
    }

    fun resetOperationsInfo(operationList: List<OperationType>) {
        operations.clear()
        operations.addAll(operationList)
    }
}