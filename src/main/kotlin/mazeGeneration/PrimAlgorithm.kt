package mazeGeneration

import Node


object PrimAlgorithm {

    /**
     * Generates a maze using Prim algorithm.
     */
    fun generateMazePrim(){
        Grid.makeAllWall()

        var currentNode = Grid.grid[0][0]
        val visited = mutableListOf(currentNode)
        val toVisit = mutableListOf<Node>()
        var neutralCount = 0

        Grid.getNodeNeighbours(currentNode).forEach { neighbour ->
            neighbour.parent = currentNode
            toVisit.add(neighbour)
        }

        while (toVisit.isNotEmpty()){
            GridPanel.repaint()

            currentNode = toVisit.random()
            toVisit.remove(currentNode)
            visited.add(currentNode)

            currentNode.type = NodeType.NEUTRAL
            currentNode.parent!!.type = NodeType.NEUTRAL
            neutralCount += 2


            Grid.getNodeNeighbours(currentNode).forEach { neighbour ->
                if (neighbour !in visited && neighbour !in toVisit){
                    neighbour.parent = currentNode
                    toVisit.add(neighbour)
                }
                else if(neighbour in toVisit) {
                    toVisit.remove(neighbour)
                    visited.add(neighbour)
                }
            }
        }
        Grid.totalNodes = neutralCount
        Grid.totalWalls = Grid.ROWS * Grid.ROWS - neutralCount

    }
}
