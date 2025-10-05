import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class Board extends JTable implements UpdateCells {

    public Board(int gameWidth, int gameHeight, int cellSize, PacMan pm, Ghost Blinky, Ghost Pinky, Ghost Inky, Ghost Clyde, GameWindow gw) {

        CustomBoardCellRender render = new CustomBoardCellRender();
        BoardModel model = new BoardModel(gameHeight, gameWidth, cellSize, pm, Blinky, Pinky, Inky, Clyde, gw);
        TableColumnModel columnModel = this.getColumnModel();

        this.setModel( model );
        this.setDefaultRenderer( Object.class, render );
        this.setVisible(true);
        this.setFocusable(false);
        this.setRowHeight(cellSize);

        for (int i = 0; i < columnModel.getColumnCount(); i++)
            columnModel.getColumn(i).setPreferredWidth(cellSize);

    }

    public int changeSize(int jPanelWidth, int jPanelHeight, int gameWidth, int gameHeight, PacMan pm, Ghost Blinky, Ghost Pinky, Ghost Inky, Ghost Clyde){

        int oldCellSize;

        int cellSize = Math.min( jPanelWidth / (gameWidth + 2), jPanelHeight / (gameHeight + 2) );
        if (cellSize < 1)
            cellSize = 1;
        this.setRowHeight( cellSize );

        TableColumnModel columnModel = this.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++)
            columnModel.getColumn(i).setPreferredWidth(cellSize);

        BoardModel boardModel = (BoardModel)this.getModel();
        oldCellSize = boardModel.getCellSideSize();

        pm.setSideLength((int)((float)cellSize / 1.1));
        pm.setX0( (int)((float)pm.getX0() / oldCellSize * cellSize ) );
        pm.setY0( (int)((float)pm.getY0() / oldCellSize * cellSize ) );
        pm.setX1( (int)((float)pm.getX1() / oldCellSize * cellSize ) );
        pm.setY1( (int)((float)pm.getY1() / oldCellSize * cellSize ) );

        Blinky.setSideLength((int)((float)cellSize / 1.1));
        Blinky.setX0( (int)((float)Blinky.getX0() / oldCellSize * cellSize ) );
        Blinky.setY0( (int)((float)Blinky.getY0() / oldCellSize * cellSize ) );
        Blinky.setX1( (int)((float)Blinky.getX1() / oldCellSize * cellSize ) );
        Blinky.setY1( (int)((float)Blinky.getY1() / oldCellSize * cellSize ) );

        Pinky.setSideLength((int)((float)cellSize / 1.1));
        Pinky.setX0( (int)((float)Pinky.getX0() / oldCellSize * cellSize ) );
        Pinky.setY0( (int)((float)Pinky.getY0() / oldCellSize * cellSize ) );
        Pinky.setX1( (int)((float)Pinky.getX1() / oldCellSize * cellSize ) );
        Pinky.setY1( (int)((float)Pinky.getY1() / oldCellSize * cellSize ) );

        Inky.setSideLength((int)((float)cellSize / 1.1));
        Inky.setX0( (int)((float)Inky.getX0() / oldCellSize * cellSize ) );
        Inky.setY0( (int)((float)Inky.getY0() / oldCellSize * cellSize ) );
        Inky.setX1( (int)((float)Inky.getX1() / oldCellSize * cellSize ) );
        Inky.setY1( (int)((float)Inky.getY1() / oldCellSize * cellSize ) );

        Clyde.setSideLength((int)((float)cellSize / 1.1));
        Clyde.setX0( (int)((float)Clyde.getX0() / oldCellSize * cellSize ) );
        Clyde.setY0( (int)((float)Clyde.getY0() / oldCellSize * cellSize ) );
        Clyde.setX1( (int)((float)Clyde.getX1() / oldCellSize * cellSize ) );
        Clyde.setY1( (int)((float)Clyde.getY1() / oldCellSize * cellSize ) );

        boardModel.changeSize(cellSize);

        return cellSize;

    }

    @Override
    public void updateCells(int m0, int n0, int m1, int n1) {

        BoardModel boardModel = (BoardModel)this.getModel();
        boardModel.updateCells(m0, n0, m1, n1);

    }

    @Override
    public void notifyCellOfPmPresence(int m, int n, boolean present) {

        BoardModel boardModel = (BoardModel)this.getModel();
        boardModel.notifyCellOfPmPresence(m, n, present);

    }

    @Override
    public void notifyCellOfGhostPresence(GhostNames ghostName, int m, int n, boolean present) {

        BoardModel boardModel = (BoardModel)this.getModel();
        boardModel.notifyCellOfGhostPresence(ghostName, m, n, present);

    }

    public void updateCreatures(PacMan pm, Ghost Blinky, Ghost Pinky, Ghost Inky, Ghost Clyde){

        BoardModel boardModel = (BoardModel)this.getModel();
        boardModel.updateCreatures(pm, Blinky, Pinky, Inky, Clyde);

    }

}
