package commands

import utils.OperationType
import editor.ImageEditor
import factory.FilterFactory

class GrayscaleFilterCommand(
    private val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory
) : Command {
    override fun operationType(): OperationType {
        return OperationType.GrayscaleFilter
    }

    override fun execute() {
        val grayScaleFilter = filterFactory.createInstance(operationType())
        val newImage = grayScaleFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}