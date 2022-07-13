import grid.Grid
import grid.GridPanel
import mazeGeneration.MazeGeneration
import pathFinding.PathFinding
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Program main frame with grid and menu.
 */
object Frame : JFrame() {

    // Constants.
    private const val FRAME_WIDTH = 1000
    private const val FRAME_HEIGHT = 840
    private const val BORDER_SIZE = 10
    private const val TOP_BORDER = 20
    private const val BUTTON_BORDER = 5
    private const val TITLE_FONT_SIZE = 32
    private const val SUBTITLE_FONT_SIZE = 16
    private const val STATS_FONT_SIZE = 12

    // Frame and main panels.
    private val frame = JFrame()
    private val menuP = JPanel()
    private val mainCommandsP = JPanel()
    private val pathFindingP = JPanel()
    private val mazeControlsP = JPanel()
    private val nodeSelectionP = JPanel()
    private val statisticsP = JPanel()

    // Main Commands Panel
    private val menuTitle = JLabel("Controls")

    // Path Finding Controls
    private val pathFindingTitle = JLabel("Path Finding")
    private val algorithmsBox = JComboBox(PathFinding.searchAlgorithmsNames)
    private val findPathBtn = JButton("Find Path")
    private val clearBtn = JButton("Clear Grid")
    private val resetBtn = JButton("Reset Search")

    // Maze Controls
    private val mazeTitle = JLabel("Maze Generation")
    private val generateMazeBtn = JButton("Generate Maze")
    private val mazeGeneratorBox = JComboBox(MazeGeneration.mazeAlgorithmsNames)
    private val delayCheckbox = JCheckBox("Generate with delay")

    // Node Selection Controls
    private val nodesTitle = JLabel("Node Editing")
    private val nodesBox = JComboBox(nodeTypes)

    // Statistics Controls
    private val statsTitle = JLabel("Statistics")
    private val totalNodesLabel = JLabel("Total Nodes: ${Grid.totalNodes}")
    private val totalWallsLabel = JLabel("Total Walls: ${Grid.totalWalls}")
    private val visitedNodesLabel = JLabel("Visited Nodes: ${Grid.visitedNodes}")
    private val elapsedTimeLabel = JLabel("Elapsed Time (Ms): ${PathFinding.elapsedTime}")

    // Statistics label update functions
    fun updateWallsLabel() {
        totalWallsLabel.text = totalWallsLabel.text.dropLastWord() + Grid.totalWalls
    }

    fun updateTotalNodesLabel() {
        totalNodesLabel.text = totalNodesLabel.text.dropLastWord() + Grid.totalNodes
    }

    fun updateVisitedNodesLabel() {
        visitedNodesLabel.text = visitedNodesLabel.text.dropLastWord() + Grid.visitedNodes
    }

    fun updateElapsedTimeLabel() {
        elapsedTimeLabel.text = elapsedTimeLabel.text.dropLastWord() + PathFinding.elapsedTime
    }

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
        menuTitle.font = Font("ARIAL", Font.BOLD, TITLE_FONT_SIZE)
        menuTitle.alignmentX = Component.CENTER_ALIGNMENT
        menuP.add(menuTitle, BorderLayout.CENTER)

        // Main Commands Panel setup
        val subtitleFont = Font("ARIAL", Font.BOLD, SUBTITLE_FONT_SIZE)
        val titleBorder = BorderFactory.createEmptyBorder(TOP_BORDER, 0, BORDER_SIZE, 0)
        val statsFont = Font("ARIAL", Font.BOLD, STATS_FONT_SIZE)
        mainCommandsP.layout = FlowLayout(FlowLayout.CENTER, 10, 10)
        mainCommandsP.setSize(FRAME_WIDTH - GridPanel.GRID_SIDE, FRAME_HEIGHT)

        // Path Finding Panel
        pathFindingTitle.font = subtitleFont
        pathFindingTitle.alignmentX = Component.CENTER_ALIGNMENT
        pathFindingTitle.border = titleBorder
        pathFindingP.add(pathFindingTitle)

        algorithmsBox.alignmentX = Component.CENTER_ALIGNMENT
        algorithmsBox.border = BorderFactory.createEmptyBorder(0, 0, BORDER_SIZE, 0)
        pathFindingP.add(algorithmsBox)

        findPathBtn.addActionListener { PathFinding.findPath() }
        findPathBtn.alignmentX = Component.CENTER_ALIGNMENT
        pathFindingP.add(findPathBtn)
        pathFindingP.add(Box.createRigidArea(Dimension(0, BUTTON_BORDER)))

        clearBtn.addActionListener { Grid.clear() }
        clearBtn.alignmentX = Component.CENTER_ALIGNMENT
        pathFindingP.add(clearBtn)
        pathFindingP.add(Box.createRigidArea(Dimension(0, BUTTON_BORDER)))

        resetBtn.addActionListener { Grid.resetNodes() }
        resetBtn.alignmentX = Component.CENTER_ALIGNMENT
        pathFindingP.add(resetBtn)

        pathFindingP.layout = BoxLayout(pathFindingP, BoxLayout.Y_AXIS)
        mainCommandsP.add(pathFindingP)

        // Maze Controls
        mazeTitle.font = subtitleFont
        mazeTitle.alignmentX = Component.CENTER_ALIGNMENT
        mazeTitle.border = titleBorder
        mazeControlsP.add(mazeTitle, BorderLayout.CENTER)

        mazeGeneratorBox.alignmentX = Component.CENTER_ALIGNMENT
        mazeGeneratorBox.border = BorderFactory.createEmptyBorder(0, 0, BORDER_SIZE, 0)
        mazeControlsP.add(mazeGeneratorBox)
        mazeControlsP.add(Box.createRigidArea(Dimension(0, BUTTON_BORDER)))

        delayCheckbox.alignmentX = Component.CENTER_ALIGNMENT
        delayCheckbox.addActionListener {
            MazeGeneration.MAZE_GENERATION_DELAY = if (delayCheckbox.isSelected) 1L else 0L
        }
        mazeControlsP.add(delayCheckbox)
        mazeControlsP.add(Box.createRigidArea(Dimension(0, BUTTON_BORDER)))

        generateMazeBtn.addActionListener { MazeGeneration.generateMaze() }
        generateMazeBtn.alignmentX = Component.CENTER_ALIGNMENT
        mazeControlsP.add(generateMazeBtn)

        mazeControlsP.layout = BoxLayout(mazeControlsP, BoxLayout.Y_AXIS)
        mainCommandsP.add(mazeControlsP)

        // Node Selection Panel setup
        nodesTitle.font = subtitleFont
        nodesTitle.alignmentX = Component.CENTER_ALIGNMENT
        nodesTitle.border = titleBorder
        nodeSelectionP.add(nodesTitle, BorderLayout.CENTER)

        nodeSelectionP.add(nodesBox)

        nodeSelectionP.layout = BoxLayout(nodeSelectionP, BoxLayout.Y_AXIS)
        mainCommandsP.add(nodeSelectionP)

        // Statistics Panel setup
        statsTitle.font = subtitleFont
        statsTitle.border = titleBorder
        statisticsP.add(statsTitle, BorderLayout.CENTER)

        totalNodesLabel.font = statsFont
        statisticsP.add(totalNodesLabel)

        totalWallsLabel.font = statsFont
        statisticsP.add(totalWallsLabel)

        visitedNodesLabel.font = statsFont
        statisticsP.add(visitedNodesLabel)

        elapsedTimeLabel.font = statsFont
        statisticsP.add(elapsedTimeLabel)

        statisticsP.layout = BoxLayout(statisticsP, BoxLayout.Y_AXIS)
        mainCommandsP.add(statisticsP)

        menuP.add(mainCommandsP)
        frame.add(menuP)

        // grid.Grid Panel setup
        GridPanel.setBounds(0, 0, GridPanel.GRID_SIDE, GridPanel.GRID_SIDE)
        GridPanel.addMouseListener(MouseEvent)
        frame.add(GridPanel)

        frame.isVisible = true
    }

    /**
     * Returns the search algorithm selected by the user.
     * @return search algorithm selected by JComboBox.
     */
    fun getSelectedSearchAlgo(): PathFinding.SearchAlgorithm =
        when (algorithmsBox.selectedItem as String) {
            "Breadth-First Search" -> PathFinding.SearchAlgorithm.BFS
            "Dijkstra" -> PathFinding.SearchAlgorithm.DIJKSTRA
            else -> PathFinding.SearchAlgorithm.A_STAR
        }

    /**
     * Returns the maze generation algorithm selected by the user.
     * @return maze generation algorithm selected by JComboBox.
     */
    fun getSelectedMazeAlgo(): MazeGeneration.MazeGenerationAlgorithm =
        when (mazeGeneratorBox.selectedItem as String) {
            "Kruskal" -> MazeGeneration.MazeGenerationAlgorithm.KRUSKAL
            "Prim" -> MazeGeneration.MazeGenerationAlgorithm.PRIM
            else -> MazeGeneration.MazeGenerationAlgorithm.BACKTRACKING
        }

    /**
     * Returns the node selected by the user.
     * @return node type selected by JComboBox.
     */
    fun getSelectedNodeType(): NodeType =
        when (nodesBox.selectedItem as String) {
            "Start" -> NodeType.START
            "End" -> NodeType.END
            else -> NodeType.WALL
        }
}
