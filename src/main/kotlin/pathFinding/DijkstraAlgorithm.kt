package pathFinding // ktlint-disable filename

import Node
import Position
import State
import dijkstraNodeComparator
import grid.Grid
import grid.GridPanel
import kotlinx.coroutines.delay
import java.util.*

/**
 * Dijkstra Algorithm.
 * @return the shortest path between start and end nodes, or null.
 */
suspend fun findPathDijkstra(diagonal: Boolean): MutableList<Node>? {
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
        // println(current)
        current.state = State.CLOSE.also { Grid.visitedNodes++ }

        if (current == Grid.end) break

        Grid.getNodeNeighbours(current, diagonal).forEach { neighbour ->
            if (neighbour.isWalkable()) {
                val newG = current.calculateNewCost(neighbour)
                if (newG < neighbour.g) {
                    neighbour.g = newG
                    neighbour.parent = current
                    pq.remove(neighbour).also { pq.add(neighbour) } // Update the node priority
                    neighbour.state = State.IN_QUEUE
                }
            }
        }
        delay(PathFinding.ANIMATION_DELAY)
        GridPanel.repaint()
    }

    return PathFinding.getPath()
}
