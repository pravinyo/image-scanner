package commands

import utils.OperationType
import editor.ImageEditor
import factory.ContrastEnhancementFactory

class HistogramEnhancementCommand(
    private val imageEditor: ImageEditor,
    private val contrastEnhancementFactory: ContrastEnhancementFactory
) : Command {
    override fun operationType(): OperationType {
        return OperationType.HistogramEnhancement
    }

    override fun execute() {
        val histogramEnhancement = contrastEnhancementFactory.createInstance(operationType())
        val newImage = histogramEnhancement.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}