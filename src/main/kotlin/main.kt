import java.awt.*
import javax.swing.*


fun main() {
    val frame = JFrame()
    frame.initFrame()

    val grid = JPanel()
    grid.background = Color.BLACK
    grid.setBounds(GRID_X, GRID_Y, GRID_SIDE, GRID_SIDE)
    grid.layout = null
    frame.add(grid)
}
