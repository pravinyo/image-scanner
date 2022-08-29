package factory

import OperationType
import OperationType.*
import contrastenhancement.*

class ContrastEnhancementFactory : ImageOperationFactory<ContrastEnhancement> {
    override fun createInstance(operationType: OperationType): ContrastEnhancement {
        return when (operationType) {
            is AdaptiveHistogramEnhancement -> AdaptiveHistogramEqualization(operationType.claheParameters)
            is GammaEnhancement -> GammaCorrection(operationType.gamma)
            is HistogramEnhancement -> HistogramEqualization()
            is ImAdjustEnhancement -> ImageContrastAdjustment(operationType.imageContrastAdjustParameters)
            is SaturationEnhancement -> SaturationCorrection(operationType.saturationCorrectionParameters)
            else -> throw IllegalArgumentException("Type of $operationType is unknown")
        }
    }
}