package commands

import utils.OperationType
import editor.ImageEditor
import factory.TransformationFactory
import transformations.TranslationTransformParameters

class TranslateCommand(
    private val imageEditor: ImageEditor,
    private val transformationFactory: TransformationFactory,
    private val translationTransformParameters: TranslationTransformParameters
) : Command {

    override fun operationType(): OperationType {
        return OperationType.TranslationTransform(translationTransformParameters)
    }

    override fun execute() {
        val translationTransform = transformationFactory.createInstance(operationType())
        val newImage = translationTransform.execute(imageEditor.getActiveImage())
        imageEditor.setActiveImage(newImage)
    }
}