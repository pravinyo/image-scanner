package commands

import utils.OperationType
import contrastenhancement.ClaheParameters
import editor.ImageEditor
import factory.ContrastEnhancementFactory

class AdaptiveHistogramEnhancementCommand(
    private val imageEditor: ImageEditor,
    private val contrastEnhancementFactory: ContrastEnhancementFactory,
    private val claheParameters: ClaheParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.AdaptiveHistogramEnhancement(claheParameters)
    }

    override fun execute() {
        val adaptiveHistogram = contrastEnhancementFactory.createInstance(operationType())
        val newImage = adaptiveHistogram.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}