package net.pepoirot.shokunin.april

fun main() {
    Application(gridSize = 10, sampleSize = 10000).start()
}

class Application(private val gridSize: Int, private val sampleSize: Int) {

    fun start() {
        println("Number of samples for each p: $sampleSize")
        countSafeExits().forEach {
            println("%.1f %.3f".format(it.key, it.value))
        }
    }

    fun countSafeExits() = (100 downTo 0 step 10)
            .map { percentage -> percentage / 100.toDouble() }
            .associateWith { occupancyRatio ->
                val successfulExits = (1..sampleSize).fold(0, { count, _ ->
                    val grid = generateDeskGrid(gridSize, occupancyRatio)
                    val startPosition = pickRandomDeskOnBackRow(grid)
                    if (canReachFrontRow(startPosition, grid)) count + 1 else count
                })
                successfulExits / sampleSize.toDouble()
            }
}
