import java.awt.*
import javax.swing.*


const val FRAME_WIDTH = 1000
const val FRAME_HEIGHT = 800
const val GRID_GAP = 1


/**
 * Program mai frame with grid and menu.
 */
object Frame {

    private val frame = JFrame()
    private val gridP = JPanel(GridLayout(ROWS, ROWS, GRID_GAP, GRID_GAP))
    private val menuP = JPanel(FlowLayout())
    private val algorithmsBox = JComboBox(Finder.values())
    private val startButton = JButton("Find Path")
    private val clearButton = JButton("Clear")


    /**
     * Object's init function.
     */
    fun init() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
        frame.contentPane.background = Color.LIGHT_GRAY
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.title = "Path Finder- by Jesus"
        frame.isResizable = false
        frame.setLocationRelativeTo(null) // Window is placed in the center of the screen

        drawGrid()
        drawMenu()

        frame.isVisible = true
    }


    /**
     * Draws the grid.
     */
    private fun drawGrid() {
        gridP.background = Color.GRAY
        for (i in 0 until ROWS) {
            for (j in 0 until ROWS) {
                val panel = JPanel()
                panel.background = Color.WHITE
                gridP.add(panel)
            }
        }
        frame.add(gridP, FlowLayout.LEFT)
    }


    /**
     * Draws the menu.
     */
    private fun drawMenu() {
        menuP.add(algorithmsBox)

        menuP.add(startButton)
        startButton.addActionListener { find() }

        menuP.add(clearButton)
        clearButton.addActionListener { Grid.clear() }

        frame.add(menuP, BorderLayout.EAST)
    }


    /**
     * Returns the algorithm selected by the user.
     */
    fun getSelectedAlgo(): Finder {
        return algorithmsBox.selectedItem as Finder
    }


    /**
     * Updated the grid.
     */
    fun updateGrid() {
        for (i in 0 until ROWS) {
            for (j in 0 until ROWS) {
                val node = Grid.getNodeAt(Position(j, i))
                val panel = gridP.getComponent(j + i * ROWS) as JPanel
                panel.background = node!!.getColor()
            }
        }
    }
}

