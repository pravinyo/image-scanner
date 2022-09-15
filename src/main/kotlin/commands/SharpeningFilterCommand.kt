package commands

import utils.OperationType
import editor.ImageEditor
import factory.FilterFactory
import filters.SharpeningFilterParameters

class SharpeningFilterCommand constructor(
    private val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory,
    private val sharpeningFilterParameters: SharpeningFilterParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.SharpeningFilter(sharpeningFilterParameters)
    }

    override fun execute() {
        val sharpeningFilter = filterFactory.createInstance(operationType())
        val newImage = sharpeningFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}