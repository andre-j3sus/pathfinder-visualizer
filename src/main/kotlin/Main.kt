import grid.Grid

/**
 * Main function of the Path Finder Visualizer.
 * @author André Jesus.
 */
fun main() {
    Grid.init()
    Frame.init()

    // Set Default Start and End nodes
    Grid.setStartNode()
    Grid.setEndNode()
}
