import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Position of the node.
 */
data class Position(val x: Int, val y: Int)


/**
 * State of the node.
 */
enum class State { OPEN, CLOSE, IN_QUEUE }


/**
 * Type of the node.
 */
enum class NodeType { START, END, WALL, NEUTRAL, PATH }

val nodeTypes = arrayOf("Start", "End", "Obstacle")


/**
 * Node.
 * @param pos position
 * @param state current state
 * @param type current type
 * @param parent node's parent
 * @param g cost
 * @param h heuristic
 */
data class Node(
    val pos: Position,
    var state: State,
    var type: NodeType = NodeType.NEUTRAL,
    var parent: Node? = null,
    var g: Float = 0f,
    var h: Float = 0f
)


val notDefinedNode = Node(Position(-1, -1), State.OPEN)


/**
 * Calculates the F from A* algorithm.
 * @return f
 */
fun Node.getF(): Float {
    return g + h
}


/**
 * Calculates the H from A* algorithm.
 * @return h
 */
fun Node.calculateH(): Float {
    return getDistance(this, Grid.end!!)
}


/**
 * Calculates G from A* algorithm.
 * @return g
 */
fun Node.calculateNewCost(node2: Node): Float {
    return g + getDistance(this, node2)
}


/**
 * Returns the color of the node.
 */
fun Node.getColor(): Color {
    return when (type) {
        NodeType.NEUTRAL -> when (state) {
            State.OPEN -> Color.WHITE
            State.IN_QUEUE -> Color.BLUE
            State.CLOSE -> Color.CYAN
        }
        NodeType.START -> Color.GREEN
        NodeType.END -> Color.RED
        NodeType.WALL -> Color.DARK_GRAY
        NodeType.PATH -> Color.YELLOW
    }
}


/**
 * Returns the true position of the node in the GridPanel.
 * @return pos
 */
fun Node.getTruePos(): Position {
    return Position(pos.x * GridPanel.NODE_SIZE, pos.y * GridPanel.NODE_SIZE)
}


/**
 * Check if a node is open and not a wall.
 * @return true if the node is walkable
 */
fun Node.isWalkable(): Boolean {
    return state == State.OPEN && type != NodeType.WALL
}


/**
 * Relative Position of a Node
 */
enum class RelativePos { TOP, BOTTOM, LEFT, RIGHT }


/**
 * Node Comparator for dijkstra algorithm comparing costs.
 */
fun dijkstraNodeComparator() =
    Comparator<Node> { n1, n2 ->
        n1.g.compareTo(n2.g)
    }


/**
 * Node Comparator for A* algorithm.
 */
fun aStarNodeComparator() =
    Comparator<Node> { n1, n2 ->
        (n1.getF()).compareTo((n2.getF()))
    }


/**
 * Returns the distance between nodes.
 */
fun getDistance(node: Node, node2: Node): Float {
    return sqrt((node.pos.x - node2.pos.x).toFloat().pow(2) + (node.pos.y - node2.pos.y).toFloat().pow(2))
}
