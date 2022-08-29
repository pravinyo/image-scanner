package commands

import ImageEditor
import OperationType
import filters.BlackAndWhiteFilter
import filters.BlackAndWhiteFilterParameters

class BlackAndWhiteFilterCommand(
    val imageEditor: ImageEditor,
    val parameters: BlackAndWhiteFilterParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.BlackAndWhiteFilter(parameters)
    }

    override fun execute() {
        val filter = BlackAndWhiteFilter(parameters)
        val newImage = filter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}