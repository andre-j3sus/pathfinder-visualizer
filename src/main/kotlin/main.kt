import java.awt.*
import javax.swing.*


fun main() {
    val frame = JFrame()
    frame.initFrame()

    val grid = JPanel()
    grid.background = Color.BLACK
    grid.setBounds(10, 10, 740, 740)
    frame.add(grid)
}
