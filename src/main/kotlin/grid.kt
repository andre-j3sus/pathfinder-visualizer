const val ROWS = 30

/**
 * Grid with the nodes.
 */
object Grid {

    var grid = Array(ROWS) { Array(ROWS) { Node(Position(-1, -1), State.OPEN) } }
    var size = ROWS
    var start: Node? = null
    var end: Node? = null


    /**
     * Sets the start node.
     */
    fun setStart(pos: Position) {
        start = getNodeAt(pos)
        start!!.type = Type.START
    }


    /**
     * Sets the end node.
     */
    fun setEnd(pos: Position) {
        end = getNodeAt(pos)
        end!!.type = Type.END
    }


    /**
     * Initializes the object.
     */
    fun init() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                grid[i][j] =
                    grid[i][j].copy(pos = Position(j, i), state = State.OPEN, type = Type.NEUTRAL, parent = null)
            }
        }
    }


    /*fun resize(newSize: Int) {
        grid = Array(newSize) { Array(newSize) { Node(Position(-1, -1), State.OPEN) } }

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                grid[i][j] = Node(Position(j, i), State.OPEN)
            }
        }
    }*/


    /**
     * Clears the grid.
     */
    fun clear() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                grid[i][j] = grid[i][j].copy(state = State.OPEN, type = Type.NEUTRAL, parent = null)
            }
        }
        start = null
        end = null
        Frame.updateGrid()
    }


    /**
     * Returns the node at the position [pos].
     */
    fun getNodeAt(pos: Position): Node? {
        if (!isValidPos(pos)) return null
        return grid[pos.y][pos.x]
    }


    /**
     * Returns true if the position [pos] is valid.
     */
    private fun isValidPos(pos: Position): Boolean {
        return grid.isNotEmpty() && pos.y in 0..grid.lastIndex && pos.x in 0..grid[0].lastIndex
    }


    /**
     * Returns a list with the [node] neighbours.
     */
    fun getNodeNeighbours(node: Node): List<Node> {
        return listOfNotNull(
            getNodeAt(Position(node.pos.x - 1, node.pos.y)),
            getNodeAt(Position(node.pos.x + 1, node.pos.y)),
            getNodeAt(Position(node.pos.x, node.pos.y - 1)),
            getNodeAt(Position(node.pos.x, node.pos.y + 1)),
        )

        /*getNodeAt(Position(node.pos.x - 1, node.pos.y - 1)),
        getNodeAt(Position(node.pos.x + 1, node.pos.y - 1)),
        getNodeAt(Position(node.pos.x - 1, node.pos.y - 1)),
        getNodeAt(Position(node.pos.x + 1, node.pos.y + 1)),*/
    }
}
