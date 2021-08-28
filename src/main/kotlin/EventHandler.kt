import java.awt.event.MouseEvent
import java.awt.event.MouseListener



object MouseEvent : MouseListener {

    override fun mouseClicked(e: MouseEvent?) {
        //TODO("Not yet implemented")
    }

    override fun mousePressed(e: MouseEvent?) {
        val node = Grid.getNodeClicked(Position(e!!.y, e.x)) ?: return
        when (Frame.getSelectedNodeType()) {
            NodeType.START -> Grid.setStartNode(node)
            NodeType.END -> Grid.setEndNode(node)
            NodeType.WALL ->node.type = if (node.type == NodeType.WALL) NodeType.NEUTRAL else NodeType.WALL
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
