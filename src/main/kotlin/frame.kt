import java.awt.*
import javax.swing.*


/**
 * Program main frame with grid and menu.
 */
object Frame {

    private const val FRAME_WIDTH = 1000
    private const val FRAME_HEIGHT = 800

    private val frame = JFrame()

    private val menuP = JPanel(FlowLayout())
    private val algorithmsBox = JComboBox(ALGORITHM.values())
    private val startBtn = JButton("Find Path")
    private val clearBtn = JButton("Clear")


    /**
     * Object's init function.
     */
    fun init() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
        frame.contentPane.background = Color.LIGHT_GRAY
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.title = "Path Finder Visualizer- by Jesus"
        frame.isResizable = false
        frame.setLocationRelativeTo(null) // Window is placed in the center of the screen

        drawMenu()

        frame.add(GridPanel)

        frame.isVisible = true
    }


    /**
     * Draws the menu.
     */
    private fun drawMenu() {
        menuP.add(algorithmsBox)

        menuP.add(startBtn)
        startBtn.addActionListener { findPath() }

        menuP.add(clearBtn)
        clearBtn.addActionListener { Grid.clear() }

        frame.add(menuP, BorderLayout.EAST)
    }


    /**
     * Returns the algorithm selected by the user.
     */
    fun getSelectedAlgo(): ALGORITHM {
        return algorithmsBox.selectedItem as ALGORITHM
    }

}

