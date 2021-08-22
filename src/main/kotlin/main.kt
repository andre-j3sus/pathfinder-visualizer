const val DIAGONAL = false


/**
 * Main function of the Path Finder Visualizer.
 */
fun main() {
    Frame.init()
    Grid.init()

    Grid.setStart(Position(10, 10))
    Grid.setEnd(Position(15, 20))


    while (true) {
        Frame.updateGrid()
    }

}
