/**
 * Grid with the nodes.
 */
object Grid {

    // Constants
    const val ROWS = 40

    var grid = Array(ROWS) { Array(ROWS) { Node(Position(-1, -1), State.OPEN) } }
    var size = ROWS
    var start: Node? = null
    var end: Node? = null


    /**
     * Sets the start node.
     * @param pos position of the new start node
     */
    fun setStart(pos: Position) {
        start = getNodeAt(pos)
        start!!.type = NodeType.START
    }


    /**
     * Sets the end node.
     * @param pos position of the new end node
     */
    fun setEnd(pos: Position) {
        end = getNodeAt(pos)
        end!!.type = NodeType.END
    }


    /**
     * Initializes the object.
     */
    fun init() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                grid[i][j] = Node(Position(j, i), State.OPEN, NodeType.NEUTRAL, parent = null, 0f, 0f)
            }
        }
    }


    /**
     * Resets the nodes preparing them for new research. Does not affect start and end nodes.
     */
    fun resetNodes() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                grid[i][j].state = State.OPEN
                grid[i][j].parent = null
                grid[i][j].g = 0f
                grid[i][j].h = 0f
                if (grid[i][j].type == NodeType.PATH) grid[i][j].type = NodeType.NEUTRAL
            }
        }

        GridPanel.repaint()
    }


    /**
     * Clears the grid.
     */
    fun clear() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                grid[i][j].state = State.OPEN
                grid[i][j].type = NodeType.NEUTRAL
            }
        }
        start = null
        end = null

        GridPanel.repaint()
    }


    /**
     * Returns the node at the position [pos].
     * @return node
     */
    fun getNodeAt(pos: Position): Node? {
        if (!isValidPos(pos)) return null
        return grid[pos.y][pos.x]
    }


    /**
     * Checks if the position [pos] is valid.
     * @return true if the position [pos] is valid.
     */
    private fun isValidPos(pos: Position): Boolean {
        return grid.isNotEmpty() && pos.y in 0..grid.lastIndex && pos.x in 0..grid[0].lastIndex
    }


    /**
     * Returns a list with the [node] neighbours.
     */
    fun getNodeNeighbours(node: Node): List<Node> {
        val neighbours = mutableListOf<Node>()

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                if (!PathFinding.DIAGONAL_SEARCH &&
                    (i == -1 && j != 0 || i == 1 && j != 0 || i != 0 && j == -1 || i != 0 && j == 1)
                ) continue

                val neighbourPos = Position(node.pos.x + i, node.pos.y + j)
                val neighbour = getNodeAt(neighbourPos) ?: continue
                neighbours.add(neighbour)
            }
        }
        return neighbours
    }
}
