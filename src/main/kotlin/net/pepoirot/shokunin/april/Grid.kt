package net.pepoirot.shokunin.april

internal typealias Grid = Array<GridRow>
internal typealias GridRow = Array<Desk>
internal data class GridPosition(val row: Int, val col: Int)
internal enum class Desk { OCCUPIED, VACANT }
