package net.pepoirot.shokunin.april

import net.pepoirot.shokunin.april.Desk.OCCUPIED
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class GridUtilsTest {

    @ParameterizedTest
    @CsvSource("10, 0.1", "10, 0.3", "10, 0.5", "10, 0.7", "10, 0.9", "10, 1.0", "30, 0.75")
    fun `Test generating the office's desk grid`(gridSize: Int, occupancy: Double) {
        generateDeskGrid(gridSize, occupancy).let { grid ->
            assertThat(grid).hasSize(gridSize).allMatch { row -> row.size == gridSize }
            val occupiedDesks = grid.fold(0, { count, row ->
                count + row.fold(0, { countRow, desk -> if (desk == OCCUPIED) countRow + 1 else countRow })
            })
            val actualOccupancy = occupiedDesks / (gridSize * gridSize).toDouble()
            assertThat(actualOccupancy).isCloseTo(occupancy, within(0.1))
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 5, 10, 15, 20, 100])
    fun `Test pick a random desk on the back row`(gridSize: Int) {
        pickRandomDeskOnBackRow(generateDeskGrid(gridSize, .5)).let { desk ->
            val backRow = gridSize - 1
            assertThat(desk.row).isEqualTo(backRow)
            assertThat(desk.col).isBetween(0, gridSize - 1)
        }
    }

}
