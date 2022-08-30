package commands

import OperationType
import editor.ImageEditor
import factory.FilterFactory
import filters.UnsharpMaskParameters

class UnsharpMaskBoostFilterCommand(
    private val imageEditor: ImageEditor,
    private val filterFactory: FilterFactory,
    private val unSharpMaskParameters: UnsharpMaskParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.UnsharpMaskBoostFilter(unSharpMaskParameters)
    }

    override fun execute() {
        val unsharpMaskBoostFilter = filterFactory.createInstance(operationType())
        val newImage = unsharpMaskBoostFilter.convert(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}