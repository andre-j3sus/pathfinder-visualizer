import java.awt.Color
import javax.swing.*


const val FRAME_WIDTH = 1000
const val FRAME_HEIGHT = 800
const val GRID_SIDE = 600
const val GRID_X = 10
const val GRID_Y = 10


fun JFrame.initFrame(){
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT)
    this.contentPane.background = Color.LIGHT_GRAY
    this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    this.title = "Path Finder- by Jesus"
    this.isResizable = false
    this.layout = null
    this.setLocationRelativeTo(null) // Window is placed in the center of the screen

    this.isVisible = true
}
