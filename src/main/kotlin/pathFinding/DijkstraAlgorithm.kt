package pathFinding

import Node
import Position
import calculateNewCost
import dijkstraNodeComparator
import isWalkable
import kotlinx.coroutines.delay
import java.util.*


object DijkstraAlgorithm {

    /**
     * Dijkstra Algorithm.
     * @return the shortest path between start and end nodes, or null.
     */
    suspend fun findPathDijkstra(): MutableList<Node>? {

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
            current.state = State.CLOSE.also { Grid.visitedNodes++ }

            if (current == Grid.end) break

            Grid.getNodeNeighbours(current).forEach { neighbour ->
                if (neighbour.isWalkable()) {
                    val newG = current.calculateNewCost(neighbour)
                    if (newG < neighbour.g) {
                        neighbour.g = newG
                        neighbour.parent = current
                        pq.remove(neighbour).also { pq.add(neighbour) } // Update the node priority
                    }
                }
            }
            delay(PathFinding.ANIMATION_DELAY)
            GridPanel.repaint()
        }

        return PathFinding.getPath()
    }
}
