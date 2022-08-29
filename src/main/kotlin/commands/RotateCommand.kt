package commands

import ImageEditor
import transformations.RotationTransformParameters
import transformations.RotationTransformation

class RotateCommand(
    private val imageEditor: ImageEditor,
    val parameters: RotationTransformParameters
) : Command {
    override fun execute() {
        val rotationTransform = RotationTransformation(parameters)
        val newImage = rotationTransform.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}