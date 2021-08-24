import java.util.*
import kotlinx.coroutines.*


/**
 * Types of search algorithms.
 */
enum class Algorithm(val str: String) { BFS("Breadth-First Search"), DIJKSTRA("Dijkstra"), A_STAR("A*") }


/**
 * Finds the path.
 */
fun findPath() {
    if (Grid.start == null) {
        println("Start Node not selected!")
        return
    } else if (Grid.end == null) {
        println("End Node not selected!")
        return
    }

    GlobalScope.launch {
        when (Frame.getSelectedAlgo()) {
            Algorithm.BFS -> bfs()
            Algorithm.DIJKSTRA -> dijkstra()
            Algorithm.A_STAR -> aAsterisk()
        }
    }
}


/**
 * Breadth-First Search Algorithm.
 * @return the shortest path between start and end nodes, or null.
 */
suspend fun bfs(): MutableList<Node>? {
    Grid.resetNodes()

    val queue: Queue<Node> = LinkedList()

    queue.add(Grid.start!!)
    Grid.start!!.state = State.IN_QUEUE

    while (queue.isNotEmpty()) {
        for (i in 0 until queue.size) {
            val current = queue.poll()
            println(current)
            current.state = State.CLOSE

            if (current == Grid.end!!) {
                queue.clear()
                break
            }

            Grid.getNodeNeighbours(current).forEach { node ->
                if (node.state == State.OPEN) {
                    node.parent = current
                    queue.add(node)
                    node.state = State.IN_QUEUE
                }
            }
        }
        delay(50)
        GridPanel.repaint()
    }

    return getPath()
}


/**
 * A* algorithm.
 */
fun aAsterisk(): MutableList<Node>? {
    //TODO(To be implemented)
    return null
}


/**
 * Dijkstra Algorithm.
 * @return the shortest path between start and end nodes, or null.
 */
suspend fun dijkstra(): MutableList<Node>? {

    for (i in 0 until Grid.size) {
        for (j in 0 until Grid.size) {
            val node = Grid.getNodeAt(Position(j, i))!!
            node.g = Float.MAX_VALUE
            node.parent = null
        }
    }
    Grid.start!!.g = 0f

    val pq = PriorityQueue(nodeComparator())
    for (i in 0 until Grid.size) {
        pq.addAll(Grid.grid[i])
    }

    while (pq.isNotEmpty()) {
        val current = pq.poll()
        //println(current)
        current.state = State.CLOSE

        if (current == Grid.end) break

        Grid.getNodeNeighbours(current).forEach { neighbour ->
            if (neighbour.state == State.OPEN) {
                val newG = current.g + getDistance(current, neighbour)
                if (newG < neighbour.g) {
                    neighbour.g = newG
                    neighbour.parent = current
                    pq.remove(neighbour)
                    pq.add(neighbour)
                }
            }
        }
        delay(50)
        GridPanel.repaint()
    }

    return getPath()
}


/**
 * Gets the path after the search.
 * @return path or null
 */
fun getPath(): MutableList<Node>? {
    val path = mutableListOf<Node>()
    var current = Grid.end

    while (current != Grid.start) {
        if (current!!.type == Type.NEUTRAL) current.type = Type.PATH
        path.add(current)
        if (current.parent == null) return null
        current = current.parent!!
    }
    path.add(Grid.start!!)

    return path
}
