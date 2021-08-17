import java.util.*


fun bfs(grid: Array<Array<Node>>, start: Node, end: Node): MutableSet<Node>? {

    val queue: Queue<Node> = LinkedList()
    queue.add(start)
    start.state = State.IN_QUEUE

    while (queue.size > 0) {
        val current = queue.poll()
        current.state = State.CLOSE

        if (current == end) break

        current.getNeighbours(grid).forEach { node ->
            if (node != null && node.state == State.OPEN) {
                node.parent = current
                queue.add(node)
                node.state = State.IN_QUEUE
            }
        }
    }


    val path = mutableSetOf<Node>()
    var current = end

    while (current != start) {
        path.add(current)
        if (current.parent == null) return null
        current = current.parent!!
    }
    path.add(start)

    return path
}