package pathFinding

import Frame
import Grid
import Node
import NodeType
import kotlinx.coroutines.*


object PathFinding {

    // Constants
    const val DIAGONAL_SEARCH = false
    const val ANIMATION_DELAY = 20L
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
                SearchAlgorithm.BFS -> BreadthFirstSearch.findPathBFS()
                SearchAlgorithm.DIJKSTRA -> DijkstraAlgorithm.findPathDijkstra()
                SearchAlgorithm.A_STAR -> AStarAlgorithm.findPathAStar()
            }
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
