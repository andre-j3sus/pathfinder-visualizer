package pathFinding

import Frame
import Node
import NodeType
import grid.Grid
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object PathFinding {

    // Constants
    private const val DIAGONAL_SEARCH = false
    const val ANIMATION_DELAY = 20L
    val searchAlgorithmsNames = arrayOf("Breadth-First Search", "Dijkstra", "A*")
    var elapsedTime = 0L

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
            Grid.resetNodes()
            val initialTime = System.currentTimeMillis()

            when (Frame.getSelectedSearchAlgo()) {
                SearchAlgorithm.BFS -> findPathBFS(DIAGONAL_SEARCH)
                SearchAlgorithm.DIJKSTRA -> findPathDijkstra(DIAGONAL_SEARCH)
                SearchAlgorithm.A_STAR -> findPathAStar(DIAGONAL_SEARCH)
            }

            elapsedTime = System.currentTimeMillis() - initialTime
            Frame.updateElapsedTimeLabel()
            Frame.updateVisitedNodesLabel()
        }
    }

    /**
     * Gets the path after the search.
     * @return path or null
     */
    fun getPath(): MutableList<Node>? {
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
