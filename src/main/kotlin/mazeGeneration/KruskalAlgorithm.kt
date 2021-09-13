package mazeGeneration

import Node
import kotlinx.coroutines.delay


object KruskalAlgorithm {

    private val cells: HashMap<Node, Int> = HashMap()
    private val edges: MutableList<Node> = mutableListOf()
    private var wallsCount = 0

    /**
     * Generates a maze using Kruskal algorithm.
     */
    suspend fun generateMazeKruskal(delayTime: Long) {
        Grid.clear()
        cells.clear()
        edges.clear()
        wallsCount = 0
        Grid.makeAllWall()

        var id = 0
        for (i in 0 until Grid.ROWS) {
            for (j in 0 until Grid.ROWS) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                    cells[Grid.grid[j][i]] = id++
                }
                else {
                    edges.add(Grid.grid[j][i])
                }
            }
        }

        // Stop when all cells have been visited
        while (edges.isNotEmpty()) {
            delay(delayTime)
            val edge: Node = edges.random()
            val neighbours = Grid.getNodeNeighbours(edge, diagonal = false).filter { it in cells }

            if (neighbours.size >= 2){
                val first = neighbours.random()
                putWalls(first, (neighbours - first).random(), edge)
            }

            GridPanel.repaint()
        }

        Grid.totalNodes = Grid.ROWS * Grid.ROWS - wallsCount
        Grid.totalWalls = wallsCount
    }


    /**
     * Puts a wall from sideA -> Wall -> sideB.
     *
     * Changes sideB ID to sideA's ID
     */
    private fun putWalls(sideA: Node, sideB: Node, wall: Node) {
        if (cells[sideA] == cells[sideB] || cells[sideA] == null || cells[sideB] == null) return

        cells.forEach {
            if (it.value == cells[sideB]!!) {
                cells[it.key] = cells[sideA]!!
            }
        }

        sideA.type = NodeType.NEUTRAL
        wall.type = NodeType.NEUTRAL
        sideB.type = NodeType.NEUTRAL

        edges.remove(wall)
        wallsCount += 3
    }
}
