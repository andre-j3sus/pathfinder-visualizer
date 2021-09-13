import java.awt.event.MouseEvent
import java.awt.event.MouseListener


object MouseEvent : MouseListener {

    override fun mouseClicked(e: MouseEvent?) {
        //TODO("Not yet implemented")
    }


    override fun mousePressed(e: MouseEvent?) {
        val node = getNodeClickedInPos(Position(e!!.y, e.x)) ?: return
        when (Frame.getSelectedNodeType()) {
            NodeType.START -> Grid.setStartNode(node)
            NodeType.END -> Grid.setEndNode(node)
            NodeType.WALL -> {
                if (node.type == NodeType.WALL) {
                    node.type = NodeType.NEUTRAL
                    Grid.totalWalls--
                } else {
                    node.type = NodeType.WALL
                    Grid.totalWalls++
                }
                Frame.updateWallsLabel()
            }
            else -> {
            }
        }
        GridPanel.repaint()
    }


    /**
     * Returns the node clicked at the position [pos].
     * @return node
     */
    private fun getNodeClickedInPos(pos: Position): Node? {
        for (i in 0 until Grid.size) {
            for (j in 0 until Grid.size) {
                val node = Grid.grid[i][j]
                val truePos = node.getTruePos()

                if (pos.x in truePos.x..(truePos.x + GridPanel.NODE_SIZE) && pos.y in truePos.y..(truePos.y + GridPanel.NODE_SIZE)) {
                    return node
                }
            }
        }
        return null
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
