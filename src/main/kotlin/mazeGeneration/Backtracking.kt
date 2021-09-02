package mazeGeneration

import Node
import kotlinx.coroutines.delay
import java.util.*


object Backtracking {

    /**
     * Generates a maze by backtracking.
     */
    suspend fun generateMazeBackTracking() {
        Grid.makeAllWall()

        // This stack backtracks the maze
        val stack: Stack<Node> = Stack<Node>()

        // Keeps track of visited Nodes
        val visited: MutableSet<Node> = HashSet<Node>()

        var neutralCount = 0

        var currentNode: Node = Grid.grid[0][0]
        stack.push(currentNode)
        visited.add(currentNode)

        while (!stack.isEmpty()) {
            delay(MazeGeneration.MAZE_GENERATION_DELAY)

            // Get all neighbours visitable, checking if any neighbour of a neighbour was visited
            val neighbours = Grid.getNodeNeighbours(currentNode).filter { neighbour ->
                Grid.getNodeNeighbours(neighbour).all { (it !in visited && it != currentNode) || it == currentNode }
            }

            // If there are no available neighbours, backtrack.
            if (neighbours.isEmpty()) {
                currentNode = stack.pop()
                continue
            }

            // Pick random neighbour from not visited neighbours
            val randomNeighbor: Node = neighbours.random()
            randomNeighbor.type = NodeType.NEUTRAL.also { neutralCount++ }
            GridPanel.repaint()

            // Set picked neighbour as current node for next
            currentNode = randomNeighbor

            // Set node as visited
            visited.add(randomNeighbor)

            // Push to stack
            stack.push(randomNeighbor)
        }
        Grid.totalNodes = neutralCount
        Grid.totalWalls = Grid.ROWS * Grid.ROWS - neutralCount
    }
}
