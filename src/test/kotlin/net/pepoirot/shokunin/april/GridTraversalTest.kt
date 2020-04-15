package net.pepoirot.shokunin.april

import net.pepoirot.shokunin.april.Desk.OCCUPIED
import net.pepoirot.shokunin.april.Desk.VACANT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class GridTraversalTest {

    @ParameterizedTest
    @CsvSource(
            "(3,1) to (0,0) : true",
            "(2,1) to (0,0) : true",
            "(2,0) to (0,0) : true",
            "(1,0) to (0,0) : true",
            "(3,3) to (0,0) : false",
            "(3,3) to (0,1) : false",
            "(3,3) to (0,2) : false",
            "(3,3) to (0,3) : false",
            delimiter = ':')
    fun `Test finding a path from source to target positions`(positions: String, reachable: Boolean) {
        val grid = arrayOf(
                arrayOf(VACANT, OCCUPIED, OCCUPIED, VACANT),
                arrayOf(VACANT, OCCUPIED, OCCUPIED, VACANT),
                arrayOf(VACANT, VACANT, OCCUPIED, OCCUPIED),
                arrayOf(OCCUPIED, VACANT, OCCUPIED, VACANT)
        )

        val start = parse(positions.split("to")[0])
        val target = parse(positions.split("to")[1])

        val result = GridTraversal(grid).findPath(start, at(target), GridPosition::col)
        assertThat(result).isEqualTo(reachable)
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 10, 15, 20, 100])
    fun `Test can reach the front row on an empty grid`(gridSize: Int) {
        val grid = generateDeskGrid(gridSize, 0.0)
        for (column in 0 until gridSize) {
            assertThat(canReachFrontRow(GridPosition(gridSize - 1, column), grid)).isTrue()
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 10, 15, 20, 100])
    fun `Test cannot reach the front row from a full grid`(gridSize: Int) {
        val grid = generateDeskGrid(gridSize, 1.0)
        for (column in 0 until gridSize) {
            assertThat(canReachFrontRow(GridPosition(gridSize - 1, column), grid)).isFalse()
        }
    }

    private fun at(target: GridPosition): (GridPosition) -> Boolean =
            { position: GridPosition -> position == target }

    private fun parse(s: String) = s.split(',', limit = 2)
            .map { it.trim { !Character.isDigit(it) }.toInt() }
            .let { GridPosition(it[0], it[1]) }
}
