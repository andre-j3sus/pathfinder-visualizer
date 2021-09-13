package mazeGeneration

import Node
import kotlinx.coroutines.delay


object PrimAlgorithm {

    /**
     * Generates a maze using Prim algorithm.
     */
    suspend fun generateMazePrim(delayTime: Long) {
        Grid.makeAllWall()

        var currentNode = Grid.grid[0][0]
        val visited = mutableListOf(currentNode)
        val toVisit = mutableListOf<Node>()
        var neutralCount = 0

        Grid.getNodeNeighbours(currentNode, diagonal = false).forEach { neighbour ->
            neighbour.parent = currentNode
            toVisit.add(neighbour)
        }

        while (toVisit.isNotEmpty()) {
            GridPanel.repaint()
            delay(delayTime)

            currentNode = toVisit.random()
            toVisit.remove(currentNode)
            visited.add(currentNode)

            currentNode.type = NodeType.NEUTRAL.also { neutralCount += 1 }


            Grid.getNodeNeighbours(currentNode, diagonal = false).forEach { neighbour ->
                if (neighbour !in visited && neighbour !in toVisit) {
                    neighbour.parent = currentNode
                    toVisit.add(neighbour)
                } else if (neighbour in toVisit) {
                    toVisit.remove(neighbour)
                    visited.add(neighbour)
                }
            }
        }
        Grid.totalNodes = neutralCount
        Grid.totalWalls = Grid.ROWS * Grid.ROWS - neutralCount

    }
}
