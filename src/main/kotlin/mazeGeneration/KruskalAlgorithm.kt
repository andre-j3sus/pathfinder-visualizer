package mazeGeneration

import Node


object KruskalAlgorithm {

    private val cells: HashMap<Node, Int> = HashMap()
    private val walls: MutableList<Node> = mutableListOf()
    private var wallsCount = 0

    /**
     * Generates a maze using Kruskal algorithm.
     */
    fun generateMazeKruskal() {
        Grid.clear()
        cells.clear()
        walls.clear()
        wallsCount = 0

        var id = 0
        for (i in 0 until Grid.ROWS) {
            for (j in 0 until Grid.ROWS) {
                if (i % 2 == 0 && j % 2 == 0) cells[Grid.grid[j][i]] = id++
                else {
                    if (i == j) continue
                    walls.add(Grid.grid[j][i])
                }
            }
        }

        // Stop when all cells have been visited
        while (walls.isNotEmpty()) {
            val wall: Node = walls.random()
            walls.remove(wall)

            // If wall's y coordinate is even, we join left and right Node
            if (wall.pos.y % 2 == 0 && wall.pos.x in 1 until Grid.ROWS - 1) {
                putWalls(Grid.grid[wall.pos.y][wall.pos.x - 1], Grid.grid[wall.pos.y][wall.pos.x + 1], wall)
            }

            // If wall's x coordinate is even, we join top and down Node
            else if (wall.pos.x % 2 == 0 && wall.pos.y in 1 until Grid.ROWS - 1) {
                putWalls(Grid.grid[wall.pos.y + 1][wall.pos.x], Grid.grid[wall.pos.y - 1][wall.pos.x], wall)
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
        val bVal = cells[sideB]!!

        cells.forEach {
            if (it.value == bVal) {
                cells[it.key] = cells[sideA]!!
            }
        }

        sideA.type = NodeType.WALL
        wall.type = NodeType.WALL
        sideB.type = NodeType.WALL
        wallsCount += 3
    }
}
