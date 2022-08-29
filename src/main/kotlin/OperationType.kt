import transformations.RotationTransformParameters

sealed class OperationType {
    data class RotationTransform(
        val params: RotationTransformParameters
    ) : OperationType()
}
