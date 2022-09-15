package commands

import utils.OperationType
import contrastenhancement.ImageContrastAdjustParameters
import editor.ImageEditor
import factory.ContrastEnhancementFactory

class ImageContrastAdjustCommand(
    private val imageEditor: ImageEditor,
    private val contrastEnhancementFactory: ContrastEnhancementFactory,
    private val imageContrastAdjustParameters: ImageContrastAdjustParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.ImAdjustEnhancement(imageContrastAdjustParameters)
    }

    override fun execute() {
        val imAdjust = contrastEnhancementFactory.createInstance(operationType())
        val newImage = imAdjust.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}