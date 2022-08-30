package commands

import OperationType
import contrastenhancement.SaturationCorrectionParameters
import editor.ImageEditor
import factory.ContrastEnhancementFactory

class SaturationCorrectionCommand(
    private val imageEditor: ImageEditor,
    private val contrastEnhancementFactory: ContrastEnhancementFactory,
    private val saturationCorrectionParameters: SaturationCorrectionParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.SaturationEnhancement(saturationCorrectionParameters)
    }

    override fun execute() {
        val saturationCorrection = contrastEnhancementFactory.createInstance(operationType())
        val newImage = saturationCorrection.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}