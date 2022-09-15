package commands

import editor.ImageEditor
import utils.OperationType
import factory.FilterFactory
import filters.BlackAndWhiteFilterParameters

class BlackAndWhiteFilterCommand(
    val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory,
    val parameters: BlackAndWhiteFilterParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.BlackAndWhiteFilter(parameters)
    }

    override fun execute() {
        val blackAndWhiteFilter = filterFactory.createInstance(operationType())
        val newImage = blackAndWhiteFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}