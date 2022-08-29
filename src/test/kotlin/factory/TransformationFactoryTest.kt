package factory

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import transformations.PerspectiveTransformation
import transformations.RotationTransformParameters
import transformations.RotationTransformation

class TransformationFactoryTest {

    @Test
    fun `should return instance of rotation transformation`() {
        val transformationFactory = TransformationFactory()

        val rotationOperation =
            OperationType.RotationTransform(RotationTransformParameters.ArbitraryDirection(angle = 45.0))

        val rotationTransformation = transformationFactory.createInstance(rotationOperation)

        assertTrue(rotationTransformation is RotationTransformation)
    }

    @Test
    fun `should return instance of perspective transformation`() {
        val transformationFactory = TransformationFactory()

        val perspectiveTransformOperation =
            OperationType.PerspectiveTransform(sourcePoints = emptyList())

        val perspectiveTransform = transformationFactory.createInstance(perspectiveTransformOperation)

        assertTrue(perspectiveTransform is PerspectiveTransformation)
    }
}