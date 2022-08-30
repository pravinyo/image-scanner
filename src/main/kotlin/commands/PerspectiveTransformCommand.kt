package commands

import OperationType
import editor.ImageEditor
import factory.TransformationFactory
import org.opencv.core.Point

class PerspectiveTransformCommand(
    private val imageEditor: ImageEditor,
    private val transformationFactory: TransformationFactory,
    private val sourcePoints: List<Point>,
    private val destinationPoints: List<Point>? = null
) : Command {
    override fun operationType(): OperationType {
        return OperationType.PerspectiveTransform(sourcePoints, destinationPoints)
    }

    override fun execute() {
        val perspectiveTransform = transformationFactory.createInstance(operationType())
        val newImage = perspectiveTransform.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}