import java.awt.*
import javax.swing.*


/**
 * Program main frame with grid and menu.
 */
object Frame : JFrame() {

    private const val FRAME_WIDTH = 1000
    private const val FRAME_HEIGHT = 800

    private val frame = JFrame()

    private val menuP = JPanel()
    private val mainCmds = JPanel()
    private val nodeSelection = JPanel()
    private val menuTitle = JLabel("Menu")
    private val algorithmsBox = JComboBox(Algorithm.values().map { it.str }.toTypedArray())
    private val findPathBtn = JButton("Find Path")
    private val clearBtn = JButton("Clear")
    private val resetBtn = JButton("Reset")
    private val nodesBox = JComboBox(arrayOf("Start", "End", "Wall"))


    /**
     * Object's init function.
     */
    fun init() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
        frame.contentPane.background = Color.LIGHT_GRAY
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.title = "Path Finder Visualizer - by Jesus"
        frame.isResizable = false
        frame.setLocationRelativeTo(null)
        frame.layout = null


        menuP.layout = BoxLayout(menuP, BoxLayout.PAGE_AXIS)
        menuP.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        menuTitle.font = Font("ARIAL", Font.CENTER_BASELINE, 32)
        menuTitle.alignmentX = Component.CENTER_ALIGNMENT
        menuP.add(menuTitle, BorderLayout.CENTER)


        mainCmds.alignmentX = Component.CENTER_ALIGNMENT
        mainCmds.add(algorithmsBox)
        mainCmds.add(findPathBtn)
        findPathBtn.addActionListener { findPath() }
        mainCmds.add(clearBtn)
        clearBtn.addActionListener { Grid.clear() }
        mainCmds.add(resetBtn)
        clearBtn.addActionListener { Grid.resetNodes() }

        mainCmds.setSize(FRAME_WIDTH - GridPanel.GRID_SIDE, 100)
        menuP.add(mainCmds)

        nodeSelection.alignmentX = Component.CENTER_ALIGNMENT
        nodeSelection.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        nodeSelection.add(nodesBox)

        menuP.add(nodeSelection)

        menuP.setBounds(GridPanel.GRID_SIDE, 0, FRAME_WIDTH - GridPanel.GRID_SIDE, GridPanel.GRID_SIDE)
        frame.add(menuP)


        GridPanel.setBounds(0, 0, GridPanel.GRID_SIDE, GridPanel.GRID_SIDE)
        frame.add(GridPanel)

        frame.isVisible = true
    }


    /**
     * Returns the algorithm selected by the user.
     */
    fun getSelectedAlgo(): Algorithm {
        return when(algorithmsBox.selectedItem as String){
            Algorithm.BFS.str -> Algorithm.BFS
            Algorithm.DIJKSTRA.str -> Algorithm.DIJKSTRA
            else -> Algorithm.A_STAR
        }
    }

    fun getSelectedNodeType(): Type {
        return nodesBox.selectedItem as Type
    }

}

