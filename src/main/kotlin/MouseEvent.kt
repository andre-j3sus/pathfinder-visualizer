import java.awt.event.MouseEvent
import java.awt.event.MouseListener


object MouseEvent : MouseListener {

    override fun mouseClicked(e: MouseEvent?) {
        //TODO("Not yet implemented")
    }


    override fun mousePressed(e: MouseEvent?) {
        val node = Grid.getNodeClickedInPos(Position(e!!.y, e.x)) ?: return
        when (Frame.getSelectedNodeType()) {
            NodeType.START -> Grid.setStartNode(node)
            NodeType.END -> Grid.setEndNode(node)
            NodeType.WALL -> {
                if (node.type == NodeType.WALL) {
                    node.type = NodeType.NEUTRAL
                    Grid.totalWalls--
                }
                else {
                    node.type = NodeType.WALL
                    Grid.totalWalls++
                }
                Frame.updateWallsLabel()
            }
            else -> {}
        }
        GridPanel.repaint()
    }


    override fun mouseReleased(e: MouseEvent?) {
        //TODO("Not yet implemented")
    }


    override fun mouseEntered(e: MouseEvent?) {
        //TODO("Not yet implemented")
    }


    override fun mouseExited(e: MouseEvent?) {
        //TODO("Not yet implemented")
    }
}
