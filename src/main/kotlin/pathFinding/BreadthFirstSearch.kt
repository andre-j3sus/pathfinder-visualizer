package pathFinding

import Node
import isWalkable
import kotlinx.coroutines.delay
import java.util.*


object BreadthFirstSearch {

    /**
     * Breadth-First Search Algorithm.
     * @return the shortest path between start and end nodes, or null.
     */
    suspend fun findPathBFS(diagonal: Boolean): MutableList<Node>? {
        val queue: Queue<Node> = LinkedList()

        queue.add(Grid.start!!)
        Grid.start!!.state = State.IN_QUEUE

        while (queue.isNotEmpty()) {
            for (i in 0 until queue.size) {
                val current = queue.poll()
                current.state = State.CLOSE.also { Grid.visitedNodes++ }

                if (current == Grid.end!!) {
                    queue.clear()
                    break
                }

                Grid.getNodeNeighbours(current, diagonal).forEach { node ->
                    if (node.isWalkable()) {
                        node.parent = current
                        queue.add(node)
                        node.state = State.IN_QUEUE
                    }
                }
            }

            delay(PathFinding.ANIMATION_DELAY)
            GridPanel.repaint()
        }

        return PathFinding.getPath()
    }
}
