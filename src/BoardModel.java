import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class BoardModel extends AbstractTableModel implements UpdateCells {

    private final CustomField[][] cells;
    private int cellSideSize;
    private final GameWindow gw;

    public BoardModel(int gameHeight, int gameWidth, int cellSideSize, PacMan pm, Ghost Blinky, Ghost Pinky, Ghost Inky, Ghost Clyde, GameWindow gw){

        Color bgColor = Color.lightGray;
        Color wallColor = Color.BLUE;

        this.cellSideSize = cellSideSize;
        this.gw = gw;

        cells = new CustomField[gameHeight + 2][gameWidth + 2];
        boolean[][] setWalls = GenLabyrinthPrims.Generate(gameHeight, gameWidth);

        //setting the layout of the labyrinth:
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                cells[i][j] = new CustomField(setWalls[i][j], setWalls[i][j] ? wallColor : bgColor, cellSideSize, i, j);


        cells[gameHeight / 2 + 1][gameWidth / 2 + 1].setPoint(false);
        cells[gameHeight / 2 + 1][gameWidth / 2].setPoint(false);
        cells[gameHeight / 2 + 1][gameWidth / 2 + 2].setPoint(false);
        cells[gameHeight / 2][gameWidth / 2 + 1].setPoint(false);

        cells[gameHeight / 2][gameWidth / 2 - 1].setBackground(Color.BLACK);
        cells[gameHeight / 2][gameWidth / 2 ].setBackground(Color.BLACK);
        cells[gameHeight / 2][gameWidth / 2 + 2].setBackground(Color.BLACK);
        cells[gameHeight / 2][gameWidth / 2 + 3].setBackground(Color.BLACK);

        cells[gameHeight / 2 + 1][gameWidth / 2 - 1].setBackground(Color.BLACK);
        cells[gameHeight / 2 + 1][gameWidth / 2 + 3].setBackground(Color.BLACK);

        cells[gameHeight / 2 + 2][gameWidth / 2 - 1].setBackground(Color.BLACK);
        cells[gameHeight / 2 + 2][gameWidth / 2 ].setBackground(Color.BLACK);
        cells[gameHeight / 2 + 2][gameWidth / 2 + 1].setBackground(Color.BLACK);
        cells[gameHeight / 2 + 2][gameWidth / 2].setBackground(Color.BLACK);
        cells[gameHeight / 2 + 2][gameWidth / 2 + 2].setBackground(Color.BLACK);
        cells[gameHeight / 2 + 2][gameWidth / 2 + 3].setBackground(Color.BLACK);

        //finding a random cell to spawn PacMan:
        boolean cellFound = false;
        while (!cellFound){

            int pmSpawnWidth = (int)(Math.floor(Math.random() * (gameWidth) + 1));
            int pmSpawnHeight = (int)(Math.floor(Math.random() * (gameHeight) + 1));

            if (
                    !cells[pmSpawnHeight][pmSpawnWidth].isWall() &&
                            ( pmSpawnHeight != gameHeight / 2 + 1 && pmSpawnHeight != gameHeight / 2 && pmSpawnHeight != gameHeight / 2 - 1 && pmSpawnHeight != gameHeight / 2 - 2 ||
                                    pmSpawnHeight == gameHeight / 2 && pmSpawnWidth != gameWidth / 2 + 1 ||
                                    pmSpawnHeight == gameHeight / 2 + 1 && pmSpawnWidth != gameWidth / 2 && pmSpawnWidth != gameWidth / 2 + 1  && pmSpawnWidth != gameWidth / 2 + 2 ||
                                    pmSpawnHeight == gameHeight / 2 - 1 && pmSpawnWidth != gameWidth / 2 && pmSpawnWidth != gameWidth / 2 + 1  && pmSpawnWidth != gameWidth / 2 + 2 ||
                                    pmSpawnHeight == gameHeight / 2 - 2 && pmSpawnWidth != gameWidth / 2 + 1
                            )
            ){
                cellFound = true;
                pm.setM0(pmSpawnHeight, false, false);
                pm.setN0(pmSpawnWidth, false, false);
                pm.setX0( (cellSideSize - pm.getSideLength()) / 2 );
                pm.setY0( (cellSideSize - pm.getSideLength()) / 2 );
                pm.setField0OnlyField(true);
                pm.setField1OnlyField(false);
                cells[pmSpawnHeight][pmSpawnWidth].setPoint(false);
                cells[pmSpawnHeight][pmSpawnWidth].paintPacMan(pm);

            }

        }

        //placing ghosts in the middle:
        Blinky.setM0(gameHeight / 2 + 1, false, false);
        Blinky.setN0(gameWidth / 2, false, false);
        Blinky.setX0(cellSideSize / 11);
        Blinky.setY0(cellSideSize / 11);
        Blinky.setField0OnlyField(true);
        Blinky.setField1OnlyField(false);
        cells[gameHeight / 2 + 1][gameWidth / 2].paintGhost(Blinky);

        Pinky.setM0(gameHeight / 2 + 1, false, false);
        Pinky.setN0(gameWidth / 2 + 2, false, false);
        Pinky.setX0(cellSideSize / 11);
        Pinky.setY0(cellSideSize / 11);
        Pinky.setField0OnlyField(true);
        Pinky.setField1OnlyField(false);
        cells[gameHeight / 2 + 1][gameWidth / 2 + 2].paintGhost(Pinky);

        Inky.setM0(gameHeight / 2 + 1, false, false);
        Inky.setN0(gameWidth / 2 + 1, false, false);
        Pinky.setX0(cellSideSize / 11);
        Pinky.setY0(cellSideSize / 11);
        Inky.setField0OnlyField(true);
        Inky.setField1OnlyField(false);
        cells[gameHeight / 2 + 1][gameWidth / 2 + 1].paintGhost(Inky);

        Clyde.setM0(gameHeight / 2, false, false);
        Clyde.setN0(gameWidth / 2 + 1, false, false);
        Pinky.setX0(cellSideSize / 11);
        Pinky.setY0(cellSideSize / 11);
        Clyde.setField0OnlyField(true);
        Clyde.setField1OnlyField(false);
        cells[gameHeight / 2][gameWidth / 2 + 1].paintGhost(Clyde);

        cells[gameHeight / 2 + 1][gameWidth / 2 + 1].setPoint(false);
        cells[gameHeight / 2 + 1][gameWidth / 2].setPoint(false);
        cells[gameHeight / 2 + 1][gameWidth / 2 + 2].setPoint(false);
        cells[gameHeight / 2][gameWidth / 2 + 1].setPoint(false);

        //generating value maps for ghosts:
        Pinky.genDirections(false, cells);
        Inky.genDirections(false, cells);
        Blinky.genDirections(false, cells);
        Clyde.genDirections(false, cells);

    }

    public void changeSize (int cellSize){

        cellSideSize = cellSize;

        for (CustomField[] i : cells)
            for (CustomField customField : i) {
                customField.rescale(cellSize);
            }
    }

    public int getCellSideSize(){
        return cellSideSize;
    }

    @Override
    public int getRowCount() {
        return cells.length;
    }

    @Override
    public int getColumnCount() {
        return cells[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

    @Override
    public void updateCells(int m0, int n0, int m1, int n1) {

        if (m0 != -1) {
            cells[m0][n0].updateContents();
            fireTableCellUpdated(m0, n0);
        }

        if (m1 != -1) {
            cells[m1][n1].updateContents();
            fireTableCellUpdated(m1, n1);
        }

    }

    public void notifyCellOfPmPresence(int m, int n, boolean present){

        cells[m][n].setHasPacMan(present);
        fireTableCellUpdated(m, n);

    }

    public void notifyCellOfGhostPresence(GhostNames ghostName, int m, int n, boolean present){

        if (ghostName == GhostNames.Blinky)
            cells[m][n].setHasBlinky(present);
        else if (ghostName == GhostNames.Pinky)
            cells[m][n].setHasPinky(present);
        else if (ghostName == GhostNames.Inky)
            cells[m][n].setHasInky(present);
        else
            cells[m][n].setHasClyde(present);

        fireTableCellUpdated(m, n);

    }

    public void updateCreatures(PacMan pm, Ghost Blinky, Ghost Pinky, Ghost Inky, Ghost Clyde){

        for (CustomField[] i : cells)
            for (CustomField customField : i)
                if (!customField.isWall()) {

                    customField.setPm(pm);
                    customField.setBlinky(Blinky);
                    customField.setPinky(Pinky);
                    customField.setInky(Inky);
                    customField.setClyde(Clyde);

                }

    }

    public CustomField[][] getCells(){
        return cells;
    }

    public void setCellHasPoint(int m, int n, boolean hasPoint){
        cells[m][n].setPoint(hasPoint);
    }

    public void replenishPoints(){

        int noOfPoints = 0;

        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++) {

                int height = cells.length - 2;
                int width = cells[i].length - 2;

                if (
                        !cells[i][j].isWall() && !cells[i][j].hasPowerUp() && !cells[i][j].hasPacMan() &&
                                !(i == height / 2 + 1 && j == width / 2 + 1) &&
                                !(i == height / 2 + 1 && j == width / 2) &&
                                !(i == height / 2 + 1 && j == width / 2 + 2) &&
                                !(i == height / 2 && j == width / 2 + 1)
                ) {

                    cells[i][j].setPoint(true);
                    noOfPoints++;
                    fireTableCellUpdated(i, j);

                }
            }

        gw.setNoOfPointsLeft(noOfPoints);

    }

    public void setPowerUpsVisibility(boolean visibile){

        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                if (cells[i][j].hasPowerUp()){

                    cells[i][j].setPowerUpVisible(visibile);
                    fireTableCellUpdated(i, j);

                }

    }

    public void createPowerUp(int m, int n){

        cells[m][n].setHasPowerUp(true);
        cells[m][n].setPowerUpVisible(true);

        if (cells[m][n].hasPoint()){
            cells[m][n].setPoint(false);
            gw.subtractOnePoint();
        }

        fireTableCellUpdated(m, n);

    }

    public void deletePowerUp(int m, int n){

        cells[m][n].setHasPowerUp(false);
        fireTableCellUpdated(m, n);

    }

}
