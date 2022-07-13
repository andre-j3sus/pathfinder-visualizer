package pathFinding // ktlint-disable filename

import Node
import Position
import State
import aStarNodeComparator
import grid.Grid
import grid.GridPanel
import kotlinx.coroutines.delay
import java.util.*

/**
 * A* algorithm.
 */
suspend fun findPathAStar(diagonal: Boolean): MutableList<Node>? {
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
            // println(current)
            current.state = State.CLOSE.also { Grid.visitedNodes++ }

            if (current == Grid.end) {
                pq.clear()
                break
            }

            Grid.getNodeNeighbours(current, diagonal).forEach { neighbour ->
                if (neighbour.isWalkable()) {
                    val newG = current.calculateNewCost(neighbour)
                    if (newG < neighbour.g) {
                        neighbour.g = newG
                        neighbour.h = neighbour.calculateH()
                        neighbour.parent = current
                        pq.add(neighbour)
                        neighbour.state = State.IN_QUEUE
                    }
                }
            }
        }

        delay(PathFinding.ANIMATION_DELAY)
        GridPanel.repaint()
    }

    return PathFinding.getPath()
}
