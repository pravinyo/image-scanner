package commands

import OperationType
import editor.ImageEditor
import factory.ContrastEnhancementFactory

class GammaCorrectionCommand(
    private val imageEditor: ImageEditor,
    private val contrastEnhancementFactory: ContrastEnhancementFactory,
    private val gamma: Double
) : Command {
    override fun operationType(): OperationType {
        return OperationType.GammaEnhancement(gamma)
    }

    override fun execute() {
        val gammaCorrection = contrastEnhancementFactory.createInstance(operationType())
        val newImage = gammaCorrection.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}