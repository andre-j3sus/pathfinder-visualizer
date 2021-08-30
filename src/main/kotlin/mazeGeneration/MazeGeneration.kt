package mazeGeneration

import Frame
import kotlinx.coroutines.*


object MazeGeneration {

    // Constants
    const val MAZE_GENERATION_DELAY = 1L
    val mazeAlgorithmsNames = arrayOf("Backtracking", "Kruskal")


    /**
     * Types of maze generation algorithms.
     */
    enum class MazeGenerationAlgorithm { KRUSKAL, BACKTRACKING }


    /**
     * Generates a maze.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun generateMaze() {
        GlobalScope.launch {
            when (Frame.getSelectedMazeAlgo()) {
                MazeGenerationAlgorithm.KRUSKAL -> KruskalAlgorithm.generateMazeKruskal()
                MazeGenerationAlgorithm.BACKTRACKING -> BackTracking.generateMazeBackTracking()
            }
        }
    }

}
