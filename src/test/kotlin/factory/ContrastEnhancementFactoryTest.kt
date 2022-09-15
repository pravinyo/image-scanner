package factory

import contrastenhancement.GammaCorrection
import contrastenhancement.SaturationCorrection
import contrastenhancement.SaturationCorrectionParameters
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.OperationType

class ContrastEnhancementFactoryTest {

    @Test
    fun `should return instance of gamma correction`() {
        val contrastEnhancementFactory = ContrastEnhancementFactory()
        val gammaOperation = OperationType.GammaEnhancement(gamma = 0.7)

        val gammaCorrection = contrastEnhancementFactory.createInstance(gammaOperation)

        assertTrue(gammaCorrection is GammaCorrection)
    }

    @Test
    fun `should return instance of saturation correction`() {
        val contrastEnhancementFactory = ContrastEnhancementFactory()
        val saturationOperation = OperationType.SaturationEnhancement(SaturationCorrectionParameters(
            alpha = 2.0,
            beta = 20.0
        ))

        val saturationCorrection = contrastEnhancementFactory.createInstance(saturationOperation)

        assertTrue(saturationCorrection is SaturationCorrection)
    }
}