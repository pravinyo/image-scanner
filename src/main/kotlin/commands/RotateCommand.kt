package commands

import editor.ImageEditor
import utils.OperationType
import factory.TransformationFactory
import transformations.RotationTransformParameters

class RotateCommand(
    private val imageEditor: ImageEditor,
    private val transformationFactory: TransformationFactory,
    val parameters: RotationTransformParameters
) : Command {

    override fun operationType(): OperationType {
        return OperationType.RotationTransform(parameters)
    }

    override fun execute() {
        val rotationTransform = transformationFactory.createInstance(operationType())
        val newImage = rotationTransform.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}