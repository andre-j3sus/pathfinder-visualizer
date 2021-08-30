import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap


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

        val cells: HashMap<Node, Int> = HashMap()
        val walls : MutableList<Node> = mutableListOf()

        var id = 0
        for (i in 0 until Grid.ROWS) {
            for (j in 0 until Grid.ROWS) {
                if (i % 2 == 0 && j % 2 == 0) cells[Grid.grid[j][i]] = id++
                else if (i == j) continue
                else walls.add(Grid.grid[j][i])
            }
        }

        fun putWalls(sideA: Node, sideB: Node, wall: Node) {
            if (cells[sideA] == cells[sideB] || cells[sideA] == null) return

            cells.forEach {
                if (it.value == cells[sideB]) cells[it.key] = cells[sideA]!!
            }

            sideA.type = NodeType.WALL
            wall.type = NodeType.WALL
            sideB.type = NodeType.WALL
        }

        // Stop when all cells have been visited
        while (walls.isNotEmpty()) {
            val wall: Node = walls[(0 until walls.size).random()]
            walls.remove(wall)

            // If wall's y coord is even, we join left and right Node
            if (wall.pos.y % 2 == 0 && wall.pos.x in 1 until Grid.ROWS - 1) {
                putWalls(Grid.grid[wall.pos.y][wall.pos.x - 1], Grid.grid[wall.pos.y][wall.pos.x + 1], wall)
            }

            // If wall's x coord is even, we join top and down Node
            else if (wall.pos.x % 2 == 0 && wall.pos.y in 1 until Grid.ROWS - 1) {
                putWalls(Grid.grid[wall.pos.y + 1][wall.pos.x], Grid.grid[wall.pos.y - 1][wall.pos.x], wall)
            }

            GridPanel.repaint()
        }

    }


}
