import java.awt.Color


data class Position(val x: Int, val y: Int)

enum class State { OPEN, CLOSE, IN_QUEUE }

enum class Type { START, END, WALL, NEUTRAL, PATH }

data class Node(val pos: Position, var state: State, var type: Type = Type.NEUTRAL, var parent: Node? = null)


/**
 * Returns the color of the node.
 */
fun Node.getColor(): Color {
    return when (type) {
        Type.NEUTRAL -> when (state) {
            State.OPEN -> Color.WHITE
            State.IN_QUEUE -> Color.GREEN
            State.CLOSE -> Color.BLUE
        }
        Type.START -> Color.YELLOW
        Type.END -> Color.MAGENTA
        Type.WALL -> Color.DARK_GRAY
        Type.PATH -> Color.RED
    }
}
