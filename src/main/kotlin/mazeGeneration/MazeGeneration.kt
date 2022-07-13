package mazeGeneration

import Frame
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object MazeGeneration {

    // Constants
    var MAZE_GENERATION_DELAY = 0L
    val mazeAlgorithmsNames = arrayOf("Backtracking", "Kruskal", "Prim")

    /**
     * Types of maze generation algorithms.
     */
    enum class MazeGenerationAlgorithm { KRUSKAL, BACKTRACKING, PRIM }

    /**
     * Generates a maze.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun generateMaze() {
        GlobalScope.launch {
            when (Frame.getSelectedMazeAlgo()) {
                MazeGenerationAlgorithm.KRUSKAL -> generateMazeKruskal(MAZE_GENERATION_DELAY)
                MazeGenerationAlgorithm.BACKTRACKING -> generateMazeBackTracking(MAZE_GENERATION_DELAY)
                MazeGenerationAlgorithm.PRIM -> generateMazePrim(MAZE_GENERATION_DELAY)
            }
            Frame.updateTotalNodesLabel()
            Frame.updateWallsLabel()
        }
    }
}
