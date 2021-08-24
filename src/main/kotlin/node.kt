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


/**
 * Node.
 * @param pos position
 * @param state current state
 * @param type current type
 * @param parent node's parent
 * @param g cost
 * @param h
 */
data class Node(
    val pos: Position,
    var state: State,
    var type: NodeType = NodeType.NEUTRAL,
    var parent: Node? = null,
    var g: Float = 0f,
    var h: Float = 0f
)


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
 * Node Comparator.
 */
fun nodeComparator() =
    Comparator<Node> { n1, n2 ->
        n1.g.compareTo(n2.g)
    }


/**
 * Returns the distance between nodes.
 */
fun getDistance(node: Node, node2: Node): Float {
    return sqrt((node.pos.x - node2.pos.x).toFloat().pow(2) + (node.pos.y - node2.pos.y).toFloat().pow(2))
}
