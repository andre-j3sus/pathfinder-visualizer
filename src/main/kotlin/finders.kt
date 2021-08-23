import java.util.*


/**
 * Types of search algorithms.
 */
enum class ALGORITHM { BFS, DIJKSTRA, A_ASTERISK }


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

    when (Frame.getSelectedAlgo()) {
        ALGORITHM.BFS -> bfs()
        ALGORITHM.DIJKSTRA -> dijkstra()
        ALGORITHM.A_ASTERISK -> aAsterisk()
    }
}


/**
 * Breadth-First Search Algorithm.
 * @return the shortest path between start and end nodes, or null.
 */
fun bfs(): MutableList<Node>? {
    Grid.resetNodes()

    val queue: Queue<Node> = LinkedList()

    queue.add(Grid.start!!)
    Grid.start!!.state = State.IN_QUEUE

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        //println(current)
        current.state = State.CLOSE

        if (current == Grid.end!!) break

        Grid.getNodeNeighbours(current).forEach { node ->
            if (node.state == State.OPEN) {
                node.parent = current
                queue.add(node)
                node.state = State.IN_QUEUE
            }
        }
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
fun dijkstra(): MutableList<Node>? {

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
