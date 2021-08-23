import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt


data class Position(val x: Int, val y: Int)

enum class State { OPEN, CLOSE, IN_QUEUE }

enum class Type { START, END, WALL, NEUTRAL, PATH }

data class Node(
    val pos: Position,
    var state: State,
    var type: Type = Type.NEUTRAL,
    var parent: Node? = null,
    var g: Float = 0f,
    var h: Float = 0f
)


/**
 * Returns the color of the node.
 */
fun Node.getColor(): Color {
    return when (type) {
        Type.NEUTRAL -> when (state) {
            State.OPEN -> Color.WHITE
            State.IN_QUEUE -> Color.BLUE
            State.CLOSE -> Color.CYAN
        }
        Type.START -> Color.GREEN
        Type.END -> Color.RED
        Type.WALL -> Color.DARK_GRAY
        Type.PATH -> Color.YELLOW
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
