import contrastenhancement.ClaheParameters
import contrastenhancement.ImageContrastAdjustParameters
import contrastenhancement.SaturationCorrectionParameters
import draw.TextParameters
import draw.TextPerspectiveTransformParameters
import filters.*
import org.opencv.core.Point
import transformations.RotationTransformParameters
import transformations.ScalingTransformParameters
import transformations.TranslationTransformParameters

sealed class OperationType {
    object UndoChange : OperationType()

    data class AddText(val textParameters: TextParameters) : OperationType()

    data class AddTextWithPerspectiveTransform(
        val textPerspectiveTransformParameters: TextPerspectiveTransformParameters
    ) : OperationType()

    data class RotationTransform(
        val rotationTransformParameters: RotationTransformParameters
    ) : OperationType()

    data class ScalingTransform(
        val scalingTransformParameters: ScalingTransformParameters
    ) : OperationType()

    data class TranslationTransform(
        val translationTransformParameters: TranslationTransformParameters
    ) : OperationType()

    data class PerspectiveTransform(
        val sourcePoints: List<Point>,
        val destPoints: List<Point>? = null
    ) : OperationType()

    data class AdaptiveHistogramEnhancement(
        val claheParameters: ClaheParameters
    ) : OperationType()

    object HistogramEnhancement : OperationType()

    data class GammaEnhancement(
        val gamma: Double
    ) : OperationType()

    data class SaturationEnhancement(
        val saturationCorrectionParameters: SaturationCorrectionParameters
    ) : OperationType()

    data class ImAdjustEnhancement(
        val imageContrastAdjustParameters: ImageContrastAdjustParameters
    ) : OperationType()

    data class BlackAndWhiteFilter(
        val blackAndWhiteFilterParameters: BlackAndWhiteFilterParameters
    ) : OperationType()

    object GrayscaleFilter : OperationType()

    object NegativeFilter : OperationType()

    data class RemoveNoiseFilter(
        val removeNoiseFilterParameters: RemoveNoiseFilterParameters
    ) : OperationType()

    data class SmoothingFilter(
        val smoothingFilterParameters: SmoothingFilterParameters
    ) : OperationType()

    data class UnsharpMaskBoostFilter(
        val unsharpMaskParameters: UnsharpMaskParameters
    ) : OperationType()

    data class SharpeningFilter(
        val sharpeningFilterParameters: SharpeningFilterParameters
    ) : OperationType()
}
