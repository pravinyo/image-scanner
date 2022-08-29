package factory

import OperationType
import OperationType.*
import transformations.*

class TransformationFactory : ImageOperationFactory<Transformation> {

    override fun createInstance(operationType: OperationType): Transformation {
        return when (operationType) {
            is RotationTransform -> RotationTransformation(operationType.rotationTransformParameters)
            is TranslationTransform -> TranslationTransformation(operationType.translationTransformParameters)
            is ScalingTransform -> ScalingTransformation(operationType.scalingTransformParameters)
            is PerspectiveTransform -> PerspectiveTransformation(operationType.sourcePoints, operationType.destPoints)
            else -> throw IllegalArgumentException("Type of $operationType is unknown")
        }
    }
}