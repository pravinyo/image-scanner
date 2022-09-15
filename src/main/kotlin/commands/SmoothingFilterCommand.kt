package commands

import utils.OperationType
import editor.ImageEditor
import factory.FilterFactory
import filters.SmoothingFilterParameters

class SmoothingFilterCommand(
    private val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory,
    private val smoothingFilterParameters: SmoothingFilterParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.SmoothingFilter(smoothingFilterParameters)
    }

    override fun execute() {
        val smoothingFilter = filterFactory.createInstance(operationType())
        val newImage = smoothingFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}