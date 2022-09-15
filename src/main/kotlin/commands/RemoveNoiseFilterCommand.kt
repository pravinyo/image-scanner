package commands

import utils.OperationType
import editor.ImageEditor
import factory.FilterFactory
import filters.RemoveNoiseFilterParameters

class RemoveNoiseFilterCommand(
    private val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory,
    private val removeNoiseFilterParameters: RemoveNoiseFilterParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.RemoveNoiseFilter(removeNoiseFilterParameters)
    }

    override fun execute() {
        val removeNoiseFilter = filterFactory.createInstance(operationType())
        val newImage = removeNoiseFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}