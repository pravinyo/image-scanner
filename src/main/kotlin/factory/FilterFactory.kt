package factory

import OperationType
import filters.*

class FilterFactory(
    private val contrastEnhancementFactory: ContrastEnhancementFactory
) : ImageOperationFactory<Filter> {
    override fun createInstance(operationType: OperationType): Filter {

        return when (operationType) {
            is OperationType.BlackAndWhiteFilter ->
                BlackAndWhiteFilter(
                    this,
                    contrastEnhancementFactory,
                    operationType.blackAndWhiteFilterParameters
                )
            is OperationType.GrayscaleFilter -> GrayscaleFilter()
            is OperationType.NegativeFilter -> NegativeFilter()
            is OperationType.RemoveNoiseFilter -> RemoveNoiseFilter(operationType.removeNoiseFilterParameters)
            is OperationType.SmoothingFilter -> SmoothingFilter(operationType.smoothingFilterParameters)
            is OperationType.UnsharpMaskBoostFilter -> UnsharpMaskBoostFilter(operationType.unsharpMaskParameters)
            is OperationType.SharpeningFilter -> SharpeningFilter(operationType.sharpeningFilterParameters)
            else -> throw IllegalArgumentException("Type of $operationType is unknown")
        }
    }
}