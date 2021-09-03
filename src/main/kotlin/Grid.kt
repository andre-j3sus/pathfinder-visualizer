import pathFinding.PathFinding

/**
 * Grid with the nodes.
 */
object Grid {

    // Constants
    const val ROWS = 40

    var grid = Array(ROWS) { Array(ROWS) { notDefinedNode } }
    var size = ROWS

    // Start and End nodes
    var start: Node? = null
    var end: Node? = null

    // Stats
    var totalNodes = size * size
    var visitedNodes = 0
    var totalWalls = 0


    /**
     * Sets the start node.
     * @param node new start node
     */
    fun setStartNode(node: Node = grid[0][0]) {
        if (node.type == NodeType.WALL) return
        if (start != null) start!!.type = NodeType.NEUTRAL
        start = node
        start!!.type = NodeType.START
    }


    /**
     * Sets the end node.
     * @param node new end node
     */
    fun setEndNode(node: Node = grid[ROWS - 1][ROWS - 1]) {
        if (node.type == NodeType.WALL) return
        if (end != null) end!!.type = NodeType.NEUTRAL
        end = node
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
        visitedNodes = 0
        Frame.updateVisitedNodesLabel()
        PathFinding.elapsedTime = 0L
        Frame.updateElapsedTimeLabel()
        GridPanel.repaint()
    }


    /**
     * Makes every node a wall.
     */
    fun makeAllWall() {
        for (i in grid.indices) {
            for (j in 0 until grid[0].size) {
                grid[i][j] = grid[i][j].copy(type = NodeType.WALL)
            }
        }
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
        totalNodes = ROWS * ROWS
        Frame.updateTotalNodesLabel()
        visitedNodes = 0
        Frame.updateVisitedNodesLabel()
        totalWalls = 0
        Frame.updateWallsLabel()

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
     * Returns the node clicked at the position [pos].
     * @return node
     */
    fun getNodeClickedInPos(pos: Position): Node? {
        for (i in 0 until size) {
            for (j in 0 until size) {
                val node = grid[i][j]
                val truePos = node.getTruePos()

                if (pos.x in truePos.x..(truePos.x + GridPanel.NODE_SIZE) && pos.y in truePos.y..(truePos.y + GridPanel.NODE_SIZE)) {
                    return node
                }
            }
        }
        return null
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
