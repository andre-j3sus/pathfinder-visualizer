import java.awt.Color
import javax.swing.*


fun JFrame.initFrame(){
    this.setSize(1000, 800)
    this.contentPane.background = Color.LIGHT_GRAY
    this.isResizable = false
    this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    this.title = "Path Finder- by Jesus"
    this.layout = null
    this.setLocationRelativeTo(null) // Window is placed in the center of the screen

    this.isVisible = true
}
