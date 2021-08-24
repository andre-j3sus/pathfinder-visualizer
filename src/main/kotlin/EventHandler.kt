import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent


object EventHandler : MouseAdapter() {

    private fun map(x: Int, in_min: Int, in_max: Int, out_min: Int, out_max: Int): Int {
        return ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min).toInt()
    }


    override fun mousePressed(e: MouseEvent) {
        val x: Int = map(e.x, 0, 500, 0, GridPanel.GRID_SIDE)
        val y: Int = map(e.y, 0, 500, 0, GridPanel.GRID_SIDE)
        val nodeClicked = Grid.getNodeAt(Position(x, y)) ?: return
        println(nodeClicked)

        nodeClicked.type = Frame.getSelectedNodeType()
    }

}