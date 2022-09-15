package commands

import utils.OperationType
import editor.ImageEditor
import factory.TransformationFactory
import transformations.ScalingTransformParameters

class ResizeCommand(
    private val imageEditor: ImageEditor,
    private val transformationFactory: TransformationFactory,
    private val scalingParameters: ScalingTransformParameters
) : Command {
    override fun operationType(): OperationType {
        return OperationType.ScalingTransform(scalingParameters)
    }

    override fun execute() {
        val scalingTransform = transformationFactory.createInstance(operationType())
        val newImage = scalingTransform.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}