package net.pepoirot.shokunin.april

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset.offset
import org.junit.jupiter.api.Test

class ApplicationTest {

    @Test
    fun `Test results are within expected ranges`() {
        Application(gridSize = 10, sampleSize = 10000).countSafeExits().let { results ->
            assertThat(results).allSatisfy { occupancyRatio, exitRatio ->
                assertThat(occupancyRatio).isBetween(0.0, 1.0)
                assertThat(exitRatio).isBetween(0.0, 1.0)
            }
        }
    }

    @Test
    fun `Test results compared to reference output`() {
        Application(gridSize = 10, sampleSize = 10000).countSafeExits().let { results ->
            assertThat(results[1.0]).isEqualTo(0.000)
            assertThat(results[0.9]).isCloseTo(0.000, offset(0.1))
            assertThat(results[0.8]).isCloseTo(0.000, offset(0.2))
            assertThat(results[0.7]).isCloseTo(0.000, offset(0.3))
            assertThat(results[0.6]).isCloseTo(0.011, offset(0.4))
            assertThat(results[0.5]).isCloseTo(0.074, offset(0.5))
            assertThat(results[0.4]).isCloseTo(0.625, offset(0.4))
            assertThat(results[0.3]).isCloseTo(0.940, offset(0.3))
            assertThat(results[0.2]).isCloseTo(1.000, offset(0.2))
            assertThat(results[0.1]).isCloseTo(1.000, offset(0.1))
            assertThat(results[0.0]).isEqualTo(1.000)
        }
    }
}
