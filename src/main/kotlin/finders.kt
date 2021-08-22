import java.util.*


enum class Finder { BFS }


/**
 * Finds the path.
 */
fun find() {
    if (Grid.start == null) {
        println("Start Node not selected!")
        return
    } else if (Grid.end == null) {
        println("End Node not selected!")
        return
    }

    when (Frame.getSelectedAlgo()) {
        Finder.BFS -> bfs(Grid)
    }
}


/**
 * Breadth-First Search Algorithm.
 */
fun bfs(grid: Grid): MutableSet<Node>? {
    val start = Grid.start!!
    val end = Grid.end!!

    val queue: Queue<Node> = LinkedList()
    queue.add(start)
    start.state = State.IN_QUEUE


    while (queue.size > 0) {
        val current = queue.poll()
        println(current)
        current.state = State.CLOSE

        if (current == end) break

        grid.getNodeNeighbours(current).forEach { node ->
            if (node.state == State.OPEN) {
                node.parent = current
                queue.add(node)
                node.state = State.IN_QUEUE
            }
        }
        Frame.updateGrid()
    }

    return getPath(start, end)
}


/*fun sleep(milli: Long){
    val initTime = System.currentTimeMillis()
    while (System.currentTimeMillis() - initTime < milli);
}*/


/**
 * Gets the path after the search.
 */
fun getPath(start: Node, end: Node): MutableSet<Node>? {
    val path = mutableSetOf<Node>()
    var current = end

    while (current != start) {
        if (current.type == Type.NEUTRAL) current.type = Type.PATH
        path.add(current)
        if (current.parent == null) return null
        current = current.parent!!
    }
    path.add(start)

    return path
}
