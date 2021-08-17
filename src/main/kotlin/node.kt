data class Position(val row: Int, val col: Int)

enum class State { OPEN, CLOSE, IN_QUEUE }

data class Node(val pos: Position, var state: State, var parent: Node? = null)


fun Node.getNeighbours(grid: Array<Array<Node>>): Array<Node?> {
    return arrayOf(
        getNodeAt(grid, Position(pos.row - 1, pos.col)),
        getNodeAt(grid, Position(pos.row + 1, pos.col)),
        getNodeAt(grid, Position(pos.row, pos.col - 1)),
        getNodeAt(grid, Position(pos.row, pos.col + 1)),
    )
}


fun Position.inGrid(grid: Array<Array<Node>>): Boolean {
    return grid.isNotEmpty() && row in 0..grid.lastIndex && col in 0..grid[0].lastIndex
}


fun getNodeAt(grid: Array<Array<Node>>, pos: Position): Node? {
    if (!pos.inGrid(grid)) return null
    return grid[pos.row][pos.col]
}



