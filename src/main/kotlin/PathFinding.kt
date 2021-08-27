import java.util.*
import kotlinx.coroutines.*


object PathFinding {

    // Constants
    const val DIAGONAL_SEARCH = false
    private const val ANIMATION_DELAY = 30L
    val searchAlgorithmsNames = arrayOf("Breadth-First Search", "Dijkstra", "A*")


    /**
     * Types of search algorithms.
     */
    enum class SearchAlgorithm { BFS, DIJKSTRA, A_STAR }


    /**
     * Finds the path.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun findPath() {
        if (Grid.start == null) {
            println("Start Node not selected!")
            return
        } else if (Grid.end == null) {
            println("End Node not selected!")
            return
        }

        GlobalScope.launch {
            when (Frame.getSelectedSearchAlgo()) {
                SearchAlgorithm.BFS -> bfs()
                SearchAlgorithm.DIJKSTRA -> dijkstra()
                SearchAlgorithm.A_STAR -> aAsterisk()
            }
        }
    }


    /**
     * Breadth-First Search Algorithm.
     * @return the shortest path between start and end nodes, or null.
     */
    private suspend fun bfs(): MutableList<Node>? {
        Grid.resetNodes()

        val queue: Queue<Node> = LinkedList()

        queue.add(Grid.start!!)
        Grid.start!!.state = State.IN_QUEUE

        while (queue.isNotEmpty()) {
            for (i in 0 until queue.size) {
                val current = queue.poll()
                //println(current)
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

            delay(ANIMATION_DELAY)
            GridPanel.repaint()
        }

        return getPath()
    }


    /**
     * A* algorithm.
     */
    private suspend fun aAsterisk(): MutableList<Node>? {
        val pq = PriorityQueue(aStarNodeComparator())

        for (i in 0 until Grid.size) {
            for (j in 0 until Grid.size) {
                val node = Grid.getNodeAt(Position(j, i))!!
                node.g = Float.MAX_VALUE
                node.parent = null
            }
        }

        Grid.start!!.g = 0f
        Grid.start!!.h = 0f
        pq.add(Grid.start)

        while (pq.isNotEmpty()) {
            for (i in 0 until pq.size) {
                val current = pq.poll()
                //println(current)
                current.state = State.CLOSE

                if (current == Grid.end) {
                    pq.clear()
                    break
                }

                Grid.getNodeNeighbours(current).forEach { neighbour ->
                    if (neighbour.state == State.OPEN) {
                        val newG = current.calculateNewCost(neighbour)
                        if (newG < neighbour.g) {
                            neighbour.g = newG
                            neighbour.h = neighbour.calculateH()
                            neighbour.parent = current
                            pq.add(neighbour)
                        }
                    }
                }
            }

            delay(ANIMATION_DELAY)
            GridPanel.repaint()
        }

        return getPath()
    }


    /**
     * Dijkstra Algorithm.
     * @return the shortest path between start and end nodes, or null.
     */
    private suspend fun dijkstra(): MutableList<Node>? {

        val pq = PriorityQueue(dijkstraNodeComparator())

        for (i in 0 until Grid.size) {
            for (j in 0 until Grid.size) {
                val node = Grid.getNodeAt(Position(j, i))!!
                node.g = if (node == Grid.start!!) 0f else Float.MAX_VALUE
                node.parent = null
                pq.add(node)
            }
        }

        while (pq.isNotEmpty()) {
            val current = pq.poll()
            //println(current)
            current.state = State.CLOSE

            if (current == Grid.end) break

            Grid.getNodeNeighbours(current).forEach { neighbour ->
                if (neighbour.state == State.OPEN) {
                    val newG = current.calculateNewCost(neighbour)
                    if (newG < neighbour.g) {
                        neighbour.g = newG
                        neighbour.parent = current
                        pq.remove(neighbour).also { pq.add(neighbour) } // Update the node priority
                    }
                }
            }
            delay(ANIMATION_DELAY)
            GridPanel.repaint()
        }

        return getPath()
    }


    /**
     * Gets the path after the search.
     * @return path or null
     */
    private fun getPath(): MutableList<Node>? {
        val path = mutableListOf<Node>()
        var current = Grid.end

        while (current != Grid.start) {
            if (current!!.type == NodeType.NEUTRAL) current.type = NodeType.PATH
            path.add(current)
            if (current.parent == null) return null
            current = current.parent!!
        }
        path.add(Grid.start!!)

        return path
    }

}
