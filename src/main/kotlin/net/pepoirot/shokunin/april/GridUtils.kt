package net.pepoirot.shokunin.april

import kotlin.random.Random

internal fun generateDeskGrid(size: Int, occupancyRatio: Double): Grid {
    return Grid(size) { GridRow(size) { Desk.VACANT } }.also {
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (Random.nextDouble() <= occupancyRatio) it[row][col] = Desk.OCCUPIED
            }
        }
    }
}

internal fun pickRandomDeskOnBackRow(grid: Grid): GridPosition {
    val backRow = grid.size - 1
    return GridPosition(backRow, grid[backRow].indices.random())
}
