import java.awt.*
import javax.swing.*


/**
 * Program main frame with grid and menu.
 */
object Frame : JFrame() {

    // Constants.
    private const val FRAME_WIDTH = 1000
    private const val FRAME_HEIGHT = 800
    private const val BORDER_SIZE = 10
    private const val FONT_SIZE = 32

    //Frame and main panels.
    private val frame = JFrame()
    private val menuP = JPanel()
    private val mainCommandsP = JPanel()
    private val nodeSelectionP = JPanel()

    // Main Commands Panel
    private val menuTitle = JLabel("Menu")
    private val algorithmsBox = JComboBox(PathFinding.algorithmsNames)
    private val findPathBtn = JButton("Find Path")
    private val clearBtn = JButton("Clear")
    private val resetBtn = JButton("Reset")

    // Node Selection Panel
    private val nodesBox = JComboBox(arrayOf("Start", "End", "Wall"))


    /**
     * Initializes the frame.
     */
    fun init() {
        // Main Frame setup
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
        frame.contentPane.background = Color.LIGHT_GRAY
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.title = "Path Finder Visualizer - by Jesus"
        frame.isResizable = false
        frame.layout = null
        frame.setLocationRelativeTo(null)

        // Menu Panel setup
        menuP.setBounds(GridPanel.GRID_SIDE, 0, FRAME_WIDTH - GridPanel.GRID_SIDE, GridPanel.GRID_SIDE)
        menuP.layout = BoxLayout(menuP, BoxLayout.PAGE_AXIS)
        menuP.border = BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        menuTitle.font = Font("ARIAL", Font.BOLD, FONT_SIZE)
        menuTitle.alignmentX = Component.CENTER_ALIGNMENT
        menuP.add(menuTitle, BorderLayout.CENTER)

        // Main Commands Panel setup
        mainCommandsP.add(algorithmsBox)
        mainCommandsP.add(findPathBtn)
        findPathBtn.addActionListener { PathFinding.findPath() }
        mainCommandsP.add(clearBtn)
        clearBtn.addActionListener { Grid.clear() }
        mainCommandsP.add(resetBtn)
        resetBtn.addActionListener { Grid.resetNodes() }
        mainCommandsP.setSize(FRAME_WIDTH - GridPanel.GRID_SIDE, 100)
        menuP.add(mainCommandsP)

        // Node Selection Panel setup
        nodeSelectionP.border = BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        nodeSelectionP.add(nodesBox)
        menuP.add(nodeSelectionP)


        frame.add(menuP)

        // Grid Panel setup
        GridPanel.setBounds(0, 0, GridPanel.GRID_SIDE, GridPanel.GRID_SIDE)
        frame.add(GridPanel)

        frame.isVisible = true
    }


    /**
     * Returns the algorithm selected by the user.
     * @return search algorithm selected by JComboBox.
     */
    fun getSelectedAlgo(): PathFinding.Algorithm {
        return when (algorithmsBox.selectedItem as String) {
            "Breadth-First Search" -> PathFinding.Algorithm.BFS
            "Dijkstra" -> PathFinding.Algorithm.DIJKSTRA
            else -> PathFinding.Algorithm.A_STAR
        }
    }


    /**
     * Returns the node selected by the user.
     * @return node type selected by JComboBox.
     */
    fun getSelectedNodeType(): NodeType {
        return nodesBox.selectedItem as NodeType
    }

}
