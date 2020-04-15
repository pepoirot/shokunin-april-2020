package net.pepoirot.shokunin.april

import java.util.Comparator.comparingInt
import java.util.PriorityQueue

internal fun canReachFrontRow(start: GridPosition, grid: Grid): Boolean {
    val heuristic = { position: GridPosition -> position.col } // how far we are from the front row
    val target = { position: GridPosition -> position.row == 0 } // whether we reached the front row
    return GridTraversal(grid).findPath(start, target, heuristic)
}

/**
 * Find a path to the target.
 *
 * @see <a href="https://en.wikipedia.org/wiki/A*_search_algorithm">A* search algorithm</a>
 */
internal class GridTraversal(private val grid: Grid) {

    private val distances = mutableMapOf<GridPosition, Int>().withDefault { Int.MAX_VALUE }
    private val priorities = mutableMapOf<GridPosition, Int>().withDefault { Int.MAX_VALUE }
    private val remaining = PriorityQueue<GridPosition>(comparingInt { position -> priorities.getValue(position) })

    fun findPath(start: GridPosition,
                 isTarget: (GridPosition) -> Boolean,
                 heuristic: (GridPosition) -> Int): Boolean {
        distances[start] = 0
        remaining.add(start)

        while (!remaining.isEmpty()) {
            val current = remaining.poll()
            if (grid[current.row][current.col] == Desk.VACANT && isTarget(current)) return true
            val distanceNeighbour = distances.getValue(current) + 1

            // try north
            check(current.row - 1, current.col, distanceNeighbour, heuristic)
            // try east
            check(current.row, current.col + 1, distanceNeighbour, heuristic)
            // try west
            check(current.row, current.col - 1, distanceNeighbour, heuristic)
            // try south
            check(current.row + 1, current.col, distanceNeighbour, heuristic)
        }

        return false
    }

    private fun check(row: Int, col: Int, distance: Int, heuristic: (GridPosition) -> Int) {
        if (row >= 0 && row < grid.size && col >= 0 && col < grid[row].size && grid[row][col] == Desk.VACANT) {
            val position = GridPosition(row, col)
            val previousDistance = distances.getValue(position)
            if (previousDistance > distance) {
                distances[position] = distance
                priorities[position] = distance + heuristic(position)
                remaining.add(position)
            }
        }
    }
}
