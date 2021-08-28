import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


object Maze {

    // Constants
    private const val MAZE_GENERATION_DELAY = 1L
    val mazeAlgorithmsNames = arrayOf("Backtracking", "Kruskal")


    /**
     * Types of maze generation algorithms.
     */
    enum class MazeGenerationAlgorithm { KRUSKAL, BACKTRACKING }


    /**
     * Generates a maze.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun generateMaze() {
        GlobalScope.launch {
            when (Frame.getSelectedMazeAlgo()) {
                MazeGenerationAlgorithm.KRUSKAL -> kruskal()
                MazeGenerationAlgorithm.BACKTRACKING -> backTracking()
            }
        }
    }


    /**
     * Generates a maze by backtracking.
     */
    private suspend fun backTracking() {
        Grid.makeAllWall()

        // This stack backtracks the maze
        val stack: Stack<Node> = Stack<Node>()

        // Keeps track of visited Nodes
        val visited: MutableSet<Node> = HashSet<Node>()

        var currentNode: Node = Grid.grid[0][0]
        stack.push(currentNode)
        visited.add(currentNode)

        while (!stack.isEmpty()) {
            delay(MAZE_GENERATION_DELAY)

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
            randomNeighbor.type = NodeType.NEUTRAL
            GridPanel.repaint()

            // Set picked neighbour as current node for next
            currentNode = randomNeighbor
            // Set node as visited
            visited.add(randomNeighbor)
            // Push to stack
            stack.push(randomNeighbor)
        }
    }


    /**
     * Generates a maze using Kruskal algorithm.
     */
    private fun kruskal() {
        //TODO("To be implemented")
    }

}
