package commands

import utils.OperationType
import editor.ImageEditor
import factory.FilterFactory

class NegativeFilterCommand(
    val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory
) : Command {
    override fun operationType(): OperationType {
        return OperationType.NegativeFilter
    }

    override fun execute() {
        val negativeFilter = filterFactory.createInstance(operationType())
        val newImage = negativeFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}