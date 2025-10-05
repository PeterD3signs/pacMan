public class MoveGhostThread extends Thread{

    private final Ghost ghost;
    private final GameWindow gw;
    private int stepLength;
    private final long sleepForMS;
    private boolean keepRunning;
    private boolean skipWaiting;
    private Directions moveDir;
    private int speedBonus;

    public MoveGhostThread(Ghost ghost, GameWindow gw, int cellSize, Directions moveDir) {
        this.ghost = ghost;
        this.gw = gw;
        this.moveDir = moveDir;
        stepLength = cellSize / 5;
        sleepForMS = 40;
        keepRunning = true;
        skipWaiting = false;
        speedBonus = 1;

    }

    public void setStepLength(int cellSize) {
        stepLength = cellSize / 5;
    }

    public void freeze(boolean set){

        speedBonus = (set ? 0 : 1);

    }

    private void move(Directions directions) {

        //for moving the PacMan and informing the board which cells to update

        int stepLength = this.stepLength * speedBonus;

        if (ghost.isField0OnlyField()) {

            if (directions == Directions.Left) {

                ghost.setX0(ghost.getX0() - stepLength);

            } else if (directions == Directions.Right) {

                ghost.setX0(ghost.getX0() + stepLength);

            } else if (directions == Directions.Down) {

                ghost.setY0(ghost.getY0() + stepLength);

            } else {        //Up

                ghost.setY0(ghost.getY0() - stepLength);

            }

            gw.updateCells(ghost.getM0(), ghost.getN0(), -1, -1);

        } else if (ghost.isField1OnlyField()) {

            if (directions == Directions.Left) {

                ghost.setX1(ghost.getX1() - stepLength);

            } else if (directions == Directions.Right) {

                ghost.setX1(ghost.getX1() + stepLength);

            } else if (directions == Directions.Down) {

                ghost.setY1(ghost.getY1() + stepLength);

            } else {        //Up

                ghost.setY1(ghost.getY1() - stepLength);

            }

            gw.updateCells(-1, -1, ghost.getM1(), ghost.getN1());

        } else {

            if (directions == Directions.Left) {

                ghost.setX0(ghost.getX0() - stepLength);
                ghost.setX1(ghost.getX1() - stepLength);

            } else if (directions == Directions.Right) {

                ghost.setX0(ghost.getX0() + stepLength);
                ghost.setX1(ghost.getX1() + stepLength);

            } else if (directions == Directions.Down) {

                ghost.setY0(ghost.getY0() + stepLength);
                ghost.setY1(ghost.getY1() + stepLength);

            } else {        //Up

                ghost.setY0(ghost.getY0() - stepLength);
                ghost.setY1(ghost.getY1() - stepLength);

            }

            gw.updateCells(ghost.getM0(), ghost.getN0(), ghost.getM1(), ghost.getN1());

        }

    }

    private void notifyCellOfGhostPresence(GhostNames ghostName , int m, int n, boolean present) {
        gw.notifyCellOfGhostPresence( ghostName, m, n, present);
    }

    private void moveLeft(boolean onlyField, boolean isField0, boolean alreadyTurn, Directions advanceInDirection, boolean dirChange){

        if (onlyField && alreadyTurn) {     //only on one field and is to left right now

            int cellSize = gw.getCellSizeFromModel();
            int ghostSize = ghost.getSideLength();
            int startPointOfMidPoint = (cellSize - ghostSize) / 2;

            if (dirChange){

                moveDir = Directions.Left;
                ghost.setDirection(Directions.Left);
                ghost.setY0(startPointOfMidPoint);
                ghost.setY1(startPointOfMidPoint);

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setX0(startPointOfMidPoint);
                    ghost.setX1(cellSize + startPointOfMidPoint);
                    ghost.setM1(ghost.getM0(), false, true);
                    ghost.setN1(ghost.getN0() - 1, true, false);


                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setX1(startPointOfMidPoint);
                    ghost.setX0(cellSize + startPointOfMidPoint);
                    ghost.setM0(ghost.getM1(), false, true);
                    ghost.setN0(ghost.getN1() - 1, true, false);

                }

            } else {

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setY1(startPointOfMidPoint);
                    ghost.setX1(cellSize + ghost.getX0());
                    ghost.setM1(ghost.getM0(), false, true);
                    ghost.setN1(ghost.getN0() - 1, true, false);


                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setY0(startPointOfMidPoint);
                    ghost.setX0(cellSize + ghost.getX1());
                    ghost.setM0(ghost.getM1(), false, true);
                    ghost.setN0(ghost.getN1() - 1, true, false);

                }

            }

            move(Directions.Left);

        } else if (onlyField){      //only on one field but should not turn yet

            move(advanceInDirection);

        } else {        //in between two fields

            int n0 = ghost.getN0();
            int n1 = ghost.getN1();

            if (n0 > n1) {       //Cell0 is on the right

                if (ghost.getX0() <= -ghost.getSideLength()) {     //Ghost fully cleared cell0

                    ghost.setField1OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), ghost.getM0(), n0, false);

                }

            } else {        //Cell1 is on the right

                if (ghost.getX1() <= -ghost.getSideLength()) {     //Ghost fully cleared cell1

                    ghost.setField0OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), ghost.getM1(), n1, false);

                }

            }

            move(Directions.Left);

        }

    }

    private void moveRight(boolean onlyField, boolean isField0, boolean alreadyTurn, Directions advanceInDirection, boolean dirChange) {

        if (onlyField && alreadyTurn) {     //only on one field and is to turn right now

            int cellSize = gw.getCellSizeFromModel();
            int ghostSize = ghost.getSideLength();
            int startPointOfMidPoint = (cellSize - ghostSize) / 2;

            if (dirChange){

                moveDir = Directions.Right;
                ghost.setDirection(Directions.Right);
                ghost.setY0(startPointOfMidPoint);
                ghost.setY1(startPointOfMidPoint);

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setX0(startPointOfMidPoint);
                    ghost.setX1(-cellSize + startPointOfMidPoint);
                    ghost.setM1(ghost.getM0(), false, true);
                    ghost.setN1(ghost.getN0() + 1, true, false);

                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setX1(startPointOfMidPoint);
                    ghost.setX0(-cellSize + startPointOfMidPoint);
                    ghost.setM0(ghost.getM1(), false, true);
                    ghost.setN0(ghost.getN1() + 1, true, false);

                }

            } else {

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setY1(startPointOfMidPoint);
                    ghost.setX1(-cellSize + ghost.getX0());
                    ghost.setM1(ghost.getM0(), false, true);
                    ghost.setN1(ghost.getN0() + 1, true, false);


                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setY0(startPointOfMidPoint);
                    ghost.setX0(-cellSize + ghost.getX1());
                    ghost.setM0(ghost.getM1(), false, true);
                    ghost.setN0(ghost.getN1() + 1, true, false);

                }

            }

            move(Directions.Right);

        } else if (onlyField) {

            move(advanceInDirection);

        } else {        //in between two fields

            int n0 = ghost.getN0();
            int n1 = ghost.getN1();

            if (n0 > n1) {       //Cell0 is on the right

                if (ghost.getX1() >= gw.getCellSizeFromModel()) {     //Ghost fully cleared cell1

                    ghost.setField0OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), ghost.getM1(), n1, false);

                }

            } else {        //Cell1 is on the right

                if (ghost.getX0() >= gw.getCellSizeFromModel()) {     //Ghost fully cleared cell0

                    ghost.setField1OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), ghost.getM0(), n0, false);

                }

            }

            move(Directions.Right);
        }

    }

    private void moveDown(boolean onlyField, boolean isField0, boolean alreadyTurn, Directions advanceInDirection, boolean dirChange) {

        if (onlyField && alreadyTurn) {     //only on one field and is to turn down now

            int cellSize = gw.getCellSizeFromModel();
            int ghostSize = ghost.getSideLength();
            int startPointOfMidPoint = (cellSize - ghostSize) / 2;

            if (dirChange){

                moveDir = Directions.Down;
                ghost.setDirection(Directions.Down);
                ghost.setX0(startPointOfMidPoint);
                ghost.setX1(startPointOfMidPoint);

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setY0(startPointOfMidPoint);
                    ghost.setY1(-cellSize + startPointOfMidPoint);
                    ghost.setM1(ghost.getM0() + 1, false, true);
                    ghost.setN1(ghost.getN0(), true, false);

                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setY1(startPointOfMidPoint);
                    ghost.setY0(-cellSize + startPointOfMidPoint);
                    ghost.setM0(ghost.getM1() + 1, false, true);
                    ghost.setN0(ghost.getN1(), true, false);

                }

            } else {

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setX1(startPointOfMidPoint);
                    ghost.setY1(-cellSize + ghost.getY0());
                    ghost.setM1(ghost.getM0() + 1, false, true);
                    ghost.setN1(ghost.getN0(), true, false);

                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setX0(startPointOfMidPoint);
                    ghost.setY0(-cellSize + ghost.getY1());
                    ghost.setM0(ghost.getM1() + 1, false, true);
                    ghost.setN0(ghost.getN1(), true, false);

                }

            }



            move(Directions.Down);

        } else if (onlyField){

            move(advanceInDirection);

        } else {        //in between two fields

            int m0 = ghost.getM0();
            int m1 = ghost.getM1();

            if (m0 > m1) {       //Cell0 is below

                if (ghost.getY1() >= gw.getCellSizeFromModel()) {     //Ghost fully cleared cell1

                    ghost.setField0OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), m1, ghost.getN1(), false);

                }

            } else {        //Cell1 is below

                if (ghost.getY0() >= gw.getCellSizeFromModel()) {     //Ghost fully cleared cell0

                    ghost.setField1OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), m0, ghost.getN0(), false);

                }

            }

            move(Directions.Down);

        }

    }

    private void moveUp(boolean onlyField, boolean isField0, boolean alreadyTurn, Directions advanceInDirection, boolean dirChange){

        if (onlyField && alreadyTurn) {     //only on one field and is to turn up now

            int cellSize = gw.getCellSizeFromModel();
            int ghostSize = ghost.getSideLength();
            int startPointOfMidPoint = (cellSize - ghostSize) / 2;

            if (dirChange){

                moveDir = Directions.Up;
                ghost.setDirection(Directions.Up);
                ghost.setX0(startPointOfMidPoint);
                ghost.setX1(startPointOfMidPoint);

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setY0(startPointOfMidPoint);
                    ghost.setY1(cellSize + startPointOfMidPoint);
                    ghost.setM1(ghost.getM0() - 1, false, true);
                    ghost.setN1(ghost.getN0(), true, false);

                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setY1(startPointOfMidPoint);
                    ghost.setY0(cellSize + startPointOfMidPoint);
                    ghost.setM0(ghost.getM1() - 1, false, true);
                    ghost.setN0(ghost.getN1(), true, false);

                }

            } else {

                if (isField0) {

                    ghost.setField0OnlyField(false);
                    ghost.setX1(startPointOfMidPoint);
                    ghost.setY1(cellSize + ghost.getY0());
                    ghost.setM1(ghost.getM0() - 1, false, true);
                    ghost.setN1(ghost.getN0(), true, false);

                } else {

                    ghost.setField1OnlyField(false);
                    ghost.setX0(startPointOfMidPoint);
                    ghost.setY0(cellSize + ghost.getY1());
                    ghost.setM0(ghost.getM1() - 1, false, true);
                    ghost.setN0(ghost.getN1(), true, false);

                }

            }

            move(Directions.Up);

        } else if (onlyField) {

            move(advanceInDirection);

        } else {        //in between two fields

            int m0 = ghost.getM0();
            int m1 = ghost.getM1();

            if (m0 > m1) {       //Cell0 is below

                if (ghost.getY0() <= -ghost.getSideLength()) {     //Ghost fully cleared cell0

                    ghost.setField1OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), m0, ghost.getN0(), false);

                }

            } else {        //Cell1 is below

                if (ghost.getY1() <= -ghost.getSideLength()) {     //Ghost fully cleared cell1

                    ghost.setField0OnlyField(true);
                    notifyCellOfGhostPresence(ghost.getGhostName(), m1, ghost.getN1(), false);

                }

            }

            move(Directions.Up);

        }

    }


    public void run() {

        while (keepRunning) {

            if (skipWaiting) {

                skipWaiting = false;

            } else {

                try {
                    sleep(sleepForMS);
                } catch (InterruptedException ex) {
                    ExceptionLog.addError(ex.getMessage());
                }

            }

            //updating values according to the map:
            int[][] valueMap = ghost.getValueMap();

            if (ghost.isField0OnlyField() || ghost.isField1OnlyField()) {       //ghost can potentially be on the final field

                int m;
                int n;
                boolean isOnField0 = true;

                if(ghost.isField1OnlyField()){

                    m = ghost.getM1();
                    n = ghost.getN1();
                    isOnField0 = false;

                } else {

                    m = ghost.getM0();
                    n = ghost.getN0();

                }

                //sides: 0 - left, 1 - up, 2 - right, 3 - down, -1 - none;
                int sideSelection = -1;
                int minimum = -1;
                int tempValue = valueMap[m][n - 1];
                int comparisonValue = valueMap[m][n];

                //checking left cell
                if (tempValue < comparisonValue && tempValue > -1) {
                    sideSelection = 0;
                    minimum = tempValue;
                }

                //checking upwards cell
                tempValue = valueMap[m - 1][n];
                if ( (minimum != -1 && tempValue < minimum || minimum == -1 && tempValue < comparisonValue ) && tempValue > -1) {
                    sideSelection = 1;
                    minimum = tempValue;
                }

                //checking right cell
                tempValue = valueMap[m][n + 1];
                if ( (minimum != -1 && tempValue < minimum || minimum == -1 && tempValue < comparisonValue ) && tempValue > -1) {
                    sideSelection = 2;
                    minimum = tempValue;
                }

                //checking downwards cell
                tempValue = valueMap[m + 1][n];
                if ( (minimum != -1 && tempValue < minimum || minimum == -1 && tempValue < comparisonValue ) && tempValue > -1) {
                    sideSelection = 3;
                }

                if (sideSelection == -1) {     //ghost is already on the cell with the lowest value (target)

                    ghost.checkForVisit(false);
                    ghost.genDirections(false, gw.getCells());
                    skipWaiting = true;

                } else {        //ghost is not yet on the cell with the lowest value (it has to move)

                    //checking whether ghost should already make a turn, or still walk forward for a bit
                    boolean alreadyTurn = false;
                    int turnPoint = (gw.getCellSizeFromModel() - ghost.getSideLength()) / 2;

                    if (moveDir == Directions.Left && (isOnField0 && ghost.getX0() <= turnPoint || !isOnField0 && ghost.getX1() <= turnPoint))
                        alreadyTurn = true;

                    if (moveDir == Directions.Right && (isOnField0 && ghost.getX0() >= turnPoint || !isOnField0 && ghost.getX1() >= turnPoint))
                        alreadyTurn = true;

                    if (moveDir == Directions.Down && (isOnField0 && ghost.getX0() >= turnPoint || !isOnField0 && ghost.getX1() >= turnPoint))
                        alreadyTurn = true;

                    if (moveDir == Directions.Up && (isOnField0 && ghost.getX0() <= turnPoint || !isOnField0 && ghost.getX1() <= turnPoint))
                        alreadyTurn = true;


                    boolean changeDir = true;

                    if (alreadyTurn){

                        if (sideSelection == 0 && moveDir == Directions.Left)
                            changeDir = false;

                        if (sideSelection == 1 && moveDir == Directions.Up)
                            changeDir = false;

                        if (sideSelection == 2 && moveDir == Directions.Right)
                            changeDir = false;

                        if (sideSelection == 3 && moveDir == Directions.Down)
                            changeDir = false;

                    }

                    //ordering to move:
                    if (sideSelection == 0){

                        moveLeft(true, isOnField0, alreadyTurn, moveDir, changeDir);

                    } else if (sideSelection == 1){

                        moveUp(true, isOnField0, alreadyTurn, moveDir, changeDir);

                    } else if (sideSelection == 2){

                        moveRight(true, isOnField0, alreadyTurn, moveDir, changeDir);

                    } else {

                        moveDown(true, isOnField0, alreadyTurn, moveDir, changeDir);

                    }

                }

            } else {        //ghost is in between fields:

                if (moveDir == Directions.Left){

                    moveLeft(false, false, false, null, false);

                } else if (moveDir == Directions.Right){

                    moveRight(false, false, false, null, false);

                } else if (moveDir == Directions.Up){

                    moveUp(false, false, false, null, false);

                } else {

                    moveDown(false, false, false, null, false);

                }

            }

        }

    }

    public void stopRunning() {

        keepRunning = false;

    }


}
