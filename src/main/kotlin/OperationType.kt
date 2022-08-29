import filters.BlackAndWhiteFilterParameters
import transformations.RotationTransformParameters

sealed class OperationType {
    data class RotationTransform(
        val params: RotationTransformParameters
    ) : OperationType()

    data class BlackAndWhiteFilter(
        val params: BlackAndWhiteFilterParameters
    ) : OperationType()
}
