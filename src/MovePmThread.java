public class MovePmThread extends Thread{

    private final PacMan pm;
    private final GameWindow gw;
    private int stepLength;
    private final long sleepForMS;
    private boolean keepRunning;
    private boolean skipWaiting;
    private Directions currentDirection;
    private Directions nextDirection;
    private Directions keyIndicated;
    private int speedBonus;

    public MovePmThread(PacMan pm, GameWindow gw, int cellSize) {
        this.pm = pm;
        this.gw = gw;
        stepLength = cellSize / 5;
        sleepForMS = 40;
        keepRunning = true;
        nextDirection = Directions.StationaryLeft;
        currentDirection = Directions.StationaryLeft;
        keyIndicated = null;
        skipWaiting = true;
        speedBonus = 1;

    }

    public void setStepLength(int cellSize){
        stepLength = cellSize / 5;
    }

    private void setNextDirection(Directions nextDirection) {

        this.nextDirection = nextDirection;

    }

    private void setCurrentDirection(Directions currentDirection) {

        this.currentDirection = currentDirection;

    }

    public void keyPressed(Directions direction) {

        keyIndicated = direction;

    }

    public void setSpeedBonus (boolean set){

        speedBonus = (set ? 3 : 1);

    }

    private void move(Directions directions) {

        //for moving the PacMan and informing the board which cells to update

        int stepLength = this.stepLength * speedBonus;

        if (pm.isField0OnlyField()) {

            if (directions == Directions.Left) {

                pm.setX0(pm.getX0() - stepLength);

            } else if (directions == Directions.Right) {

                pm.setX0(pm.getX0() + stepLength);

            } else if (directions == Directions.Down) {

                pm.setY0(pm.getY0() + stepLength);

            } else {        //Up

                pm.setY0(pm.getY0() - stepLength);

            }

            gw.updateCells(pm.getM0(), pm.getN0(), -1, -1);

        } else if (pm.isField1OnlyField()) {

            if (directions == Directions.Left) {

                pm.setX1(pm.getX1() - stepLength);

            } else if (directions == Directions.Right) {

                pm.setX1(pm.getX1() + stepLength);

            } else if (directions == Directions.Down) {

                pm.setY1(pm.getY1() + stepLength);

            } else {        //Up

                pm.setY1(pm.getY1() - stepLength);

            }

            gw.updateCells(-1, -1, pm.getM1(), pm.getN1());

        } else {

            if (directions == Directions.Left) {

                pm.setX0(pm.getX0() - stepLength);
                pm.setX1(pm.getX1() - stepLength);

            } else if (directions == Directions.Right) {

                pm.setX0(pm.getX0() + stepLength);
                pm.setX1(pm.getX1() + stepLength);

            } else if (directions == Directions.Down) {

                pm.setY0(pm.getY0() + stepLength);
                pm.setY1(pm.getY1() + stepLength);

            } else {        //Up

                pm.setY0(pm.getY0() - stepLength);
                pm.setY1(pm.getY1() - stepLength);

            }

            gw.updateCells(pm.getM0(), pm.getN0(), pm.getM1(), pm.getN1());

        }

    }

    private void notifyCellOfPmPresence(int m, int n, boolean present) {
        gw.notifyCellOfPmPresence(m, n, present);
    }

    private void updateCell(int m, int n) {
        gw.updateCells(m, n, -1, -1);
    }

    private void moveDown(){

        if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM0() + 1;
            int n = pm.getN0();

            if (gw.getCellAt(m, n).isWall()) {       //cell below is a wall;

                if (pm.getY0() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setY0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryDown);
                    pm.setDirections(Directions.StationaryDown);
                    updateCell(m - 1, n);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Down);

                }

            } else {                                //cell downwards is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int y0 = pm.getY0();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (y0 > startPointOfMidPoint) {   //PacMan moved past the midpoint of the cell

                    pm.setField0OnlyField(false);
                    pm.setM1(m, false, true);
                    pm.setN1(n, true, false);
                    pm.setX1(startPointOfMidPoint);
                    pm.setY1(-cellSize + y0);

                }

                move(Directions.Down);

            }


        } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM1() + 1;
            int n = pm.getN1();

            if (gw.getCellAt(m, n).isWall()) {       //cell downwards is a wall;

                if (pm.getY1() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setY1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryDown);
                    pm.setDirections(Directions.StationaryDown);
                    updateCell(m - 1, n);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Down);

                }

            } else {                                //cell downwards is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int y1 = pm.getY1();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (y1 > startPointOfMidPoint) {   //PacMan moved past the midpoint of the cell

                    pm.setField1OnlyField(false);
                    pm.setM0(m, false, true);
                    pm.setN0(n, true, false);
                    pm.setX0(startPointOfMidPoint);
                    pm.setY0(-cellSize + y1);

                }

                move(Directions.Down);

            }


        } else {    //there is already a different cell selected downwards;

            int m0 = pm.getM0();
            int m1 = pm.getM1();

            if (m0 > m1) {       //Cell0 is below

                if (pm.getY1() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell1

                    pm.setField0OnlyField(true);
                    notifyCellOfPmPresence(m1, pm.getN1(), false);

                }

            } else {        //Cell1 is below

                if (pm.getY0() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell0

                    pm.setField1OnlyField(true);
                    notifyCellOfPmPresence(m0, pm.getN0(), false);

                }

            }

            move(Directions.Down);

        }

    }

    private void moveUp(){

        if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM0() - 1;
            int n = pm.getN0();

            if (gw.getCellAt(m, n).isWall()) {       //cell above is a wall;

                if (pm.getY0() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setY0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryUp);
                    pm.setDirections(Directions.StationaryUp);
                    updateCell(m + 1, n);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Up);

                }

            } else {                                //cell upwards is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int y0 = pm.getY0();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (y0 < startPointOfMidPoint) {   //PacMan moved past the midpoint of the cell

                    pm.setField0OnlyField(false);
                    pm.setM1(m, false, true);
                    pm.setN1(n, true, false);
                    pm.setX1(startPointOfMidPoint);
                    pm.setY1(cellSize + y0);

                }

                move(Directions.Up);

            }


        } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM1() - 1;
            int n = pm.getN1();

            if (gw.getCellAt(m, n).isWall()) {       //cell upwards is a wall;

                if (pm.getY1() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setY1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryUp);
                    pm.setDirections(Directions.StationaryUp);
                    updateCell(m + 1, n);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Up);

                }

            } else {                                //cell upwards is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int y1 = pm.getY1();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (y1 < startPointOfMidPoint) {   //PacMan moved past the midpoint of the cell

                    pm.setField1OnlyField(false);
                    pm.setM0(m, false, true);
                    pm.setN0(n, true, false);
                    pm.setX0(startPointOfMidPoint);
                    pm.setY0(cellSize + y1);

                }

                move(Directions.Up);

            }


        } else {    //there is already a different cell selected upwards;

            int m0 = pm.getM0();
            int m1 = pm.getM1();

            if (m0 > m1) {       //Cell0 is below

                if (pm.getY0() <= -pm.getSideLength()) {     //PacMan fully cleared cell0

                    pm.setField1OnlyField(true);
                    notifyCellOfPmPresence(m0, pm.getN0(), false);

                }

            } else {        //Cell1 is below

                if (pm.getY1() <= -pm.getSideLength()) {     //PacMan fully cleared cell1

                    pm.setField0OnlyField(true);
                    notifyCellOfPmPresence(m1, pm.getN1(), false);

                }

            }

            move(Directions.Up);

        }

    }

    private void moveRight(){

        if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM0();
            int n = pm.getN0() + 1;

            if (gw.getCellAt(m, n).isWall()) {       //cell to the right is a wall;

                if (pm.getX0() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setX0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryRight);
                    pm.setDirections(Directions.StationaryRight);
                    updateCell(m, n - 1);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Right);

                }

            } else {                                //cell to the right is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int x0 = pm.getX0();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (x0 > startPointOfMidPoint) {   //PacMan moved past the midpoint of the cell

                    pm.setField0OnlyField(false);
                    pm.setM1(m, false, true);
                    pm.setN1(n, true, false);
                    pm.setX1(-cellSize + x0);
                    pm.setY1(startPointOfMidPoint);

                }

                move(Directions.Right);

            }


        } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM1();
            int n = pm.getN1() + 1;

            if (gw.getCellAt(m, n).isWall()) {       //cell to the right is a wall;

                if (pm.getX1() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setX1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryRight);
                    pm.setDirections(Directions.StationaryRight);
                    updateCell(m, n - 1);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Right);

                }

            } else {                                //cell to the right is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int x1 = pm.getX1();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (x1 > startPointOfMidPoint) {   //PacMan moved past the midpoint of the cell

                    pm.setField1OnlyField(false);
                    pm.setM0(m, false, true);
                    pm.setN0(n, true, false);
                    pm.setX0(-cellSize + x1);
                    pm.setY0(startPointOfMidPoint);

                }

                move(Directions.Right);

            }


        } else {    //there is already a different cell selected on the right;

            int n0 = pm.getN0();
            int n1 = pm.getN1();

            if (n0 > n1) {       //Cell0 is on the right

                if (pm.getX1() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell1

                    pm.setField0OnlyField(true);
                    notifyCellOfPmPresence(pm.getM1(), n1, false);

                }

            } else {        //Cell1 is on the right

                if (pm.getX0() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell0

                    pm.setField1OnlyField(true);
                    notifyCellOfPmPresence(pm.getM0(), n0, false);

                }

            }

            move(Directions.Right);

        }

    }

    private void moveLeft(){

        if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM0();
            int n = pm.getN0() - 1;

            if (gw.getCellAt(m, n).isWall()) {       //cell to the left is a wall;

                if (pm.getX0() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setX0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryLeft);
                    pm.setDirections(Directions.StationaryLeft);
                    updateCell(m, n + 1);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Left);

                }

            } else {                                //cell to the left is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int x0 = pm.getX0();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (x0 < startPointOfMidPoint && pm.isField0OnlyField()) {   //PacMan moved past the midpoint of the cell

                    pm.setField0OnlyField(false);
                    pm.setM1(m, false, true);
                    pm.setN1(n, true, false);
                    pm.setX1(cellSize + x0);
                    pm.setY1(startPointOfMidPoint);

                }

                move(Directions.Left);

            }


        } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

            int m = pm.getM1();
            int n = pm.getN1() - 1;

            if (gw.getCellAt(m, n).isWall()) {       //cell to the left is a wall;

                if (pm.getX1() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                    pm.setX1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                    setCurrentDirection(Directions.StationaryLeft);
                    pm.setDirections(Directions.StationaryLeft);
                    updateCell(m, n + 1);

                } else {        //PacMan has not yet cleared half of the field:

                    move(Directions.Left);

                }

            } else {                                //cell to the left is not a wall;

                int cellSize = gw.getCellSizeFromModel();
                int x1 = pm.getX1();
                int pmSize = pm.getSideLength();
                int startPointOfMidPoint = (cellSize - pmSize) / 2;

                if (x1 < startPointOfMidPoint && pm.isField1OnlyField()) {   //PacMan moved past the midpoint of the cell

                    pm.setField1OnlyField(false);
                    pm.setM0(m, false, true);
                    pm.setN0(n, true, false);
                    pm.setX0(cellSize + x1);
                    pm.setY0(startPointOfMidPoint);

                }

                move(Directions.Left);

            }


        } else {    //there is already a different cell selected on the left;

            int n0 = pm.getN0();
            int n1 = pm.getN1();

            if (n0 > n1) {       //Cell0 is on the right

                if (pm.getX0() <= -pm.getSideLength()) {     //PacMan fully cleared cell0

                    pm.setField1OnlyField(true);
                    notifyCellOfPmPresence(pm.getM0(), n0, false);

                }

            } else {        //Cell1 is on the right

                if (pm.getX1() <= -pm.getSideLength()) {     //PacMan fully cleared cell1

                    pm.setField0OnlyField(true);
                    notifyCellOfPmPresence(pm.getM1(), n1, false);

                }

            }

            move(Directions.Left);

        }

    }





    public void run() {

        while (keepRunning) {

            if (skipWaiting) {

                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    ExceptionLog.addError(ex.getMessage());
                }

                if (keyIndicated != null) {

                    currentDirection = keyIndicated;
                    pm.setDirections(keyIndicated);
                    nextDirection = null;
                    keyIndicated = null;
                    skipWaiting = false;

                }

            } else {

                try {
                    sleep(sleepForMS);
                } catch (InterruptedException ex) {
                    ExceptionLog.addError(ex.getMessage());
                }

                //updating values according to the key pressed by the player:

                if (keyIndicated != null) {

                    nextDirection = keyIndicated;
                    keyIndicated = null;

                }

            }

            //moving PacMan:
            if (nextDirection == null) {     //there is no change in direction

                if (currentDirection == Directions.Left){

                    moveLeft();

                } else if (currentDirection == Directions.Right) {

                    moveRight();

                } else if (currentDirection == Directions.Down) {

                    moveDown();

                } else if (currentDirection == Directions.Up) {

                    moveUp();

                }

                //There is no need to do anything if the direction is stationary

            } else if (nextDirection == Directions.Left) {   //USER DECIDED TO MOVE LEFT

                if (currentDirection == Directions.StationaryRight || currentDirection == Directions.StationaryUp || currentDirection == Directions.StationaryDown) {

                    setNextDirection(null);

                    if (pm.isField0OnlyField()) {

                        int m = pm.getM0();
                        int n = pm.getN0() - 1;

                        //checking whether the field on the left is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryLeft);
                            setCurrentDirection(Directions.StationaryLeft);
                            updateCell(m, n + 1);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Left);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Left);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(cellSize + midPoint);
                            pm.setY1(midPoint);

                            move(Directions.Left);

                        }

                    } else if (pm.isField1OnlyField()) {

                        int m = pm.getM1();
                        int n = pm.getN1() - 1;

                        //checking whether the field on the left is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryLeft);
                            setCurrentDirection(Directions.StationaryLeft);
                            updateCell(m, n + 1);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Left);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Left);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(cellSize + midPoint);
                            pm.setY0(midPoint);

                            move(Directions.Left);

                        }

                    }

                } else if (currentDirection == Directions.Left || currentDirection == Directions.Right) {

                    if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM0();
                        int n = pm.getN0() - 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the left is a wall;

                            if (pm.getX0() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setX0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryLeft);
                                pm.setDirections(Directions.StationaryLeft);
                                updateCell(m, n + 1);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Left);

                            }

                        } else {                                //cell to the left is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int x0 = pm.getX0();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (x0 < startPointOfMidPoint && pm.isField0OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField0OnlyField(false);
                                pm.setM1(m, false, true);
                                pm.setN1(n, true, false);
                                pm.setX1(cellSize + x0);
                                pm.setY1(startPointOfMidPoint);

                            }

                            move(Directions.Left);

                        }


                    } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM1();
                        int n = pm.getN1() - 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the left is a wall;

                            if (pm.getX1() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setX1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryLeft);
                                pm.setDirections(Directions.StationaryLeft);
                                updateCell(m, n + 1);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Left);

                            }

                        } else {                                //cell to the left is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int x1 = pm.getX1();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (x1 < startPointOfMidPoint && pm.isField1OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField1OnlyField(false);
                                pm.setM0(m, false, true);
                                pm.setN0(n, true, false);
                                pm.setX0(cellSize + x1);
                                pm.setY0(startPointOfMidPoint);

                            }

                            move(Directions.Left);

                        }

                    } else {        //PacMan in between fields

                        setCurrentDirection(Directions.Left);
                        pm.setDirections(Directions.Left);
                        move(Directions.Left);

                        int n0 = pm.getN0();
                        int n1 = pm.getN1();

                        if (n0 > n1) {       //Cell0 is on the right

                            if (pm.getX0() <= -pm.getSideLength()) {     //PacMan fully cleared cell0

                                pm.setField1OnlyField(true);
                                notifyCellOfPmPresence(pm.getM0(), n0, false);

                            }

                        } else {        //Cell1 is on the right

                            if (pm.getX1() <= -pm.getSideLength()) {     //PacMan fully cleared cell1

                                pm.setField0OnlyField(true);
                                notifyCellOfPmPresence(pm.getM1(), n1, false);

                            }

                        }

                    }

                } else if (currentDirection == Directions.Down || currentDirection == Directions.Up) {

                    if (pm.isField0OnlyField()) {        //can possibly turn

                        int m = pm.getM0();
                        int n = pm.getN0() - 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the left is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryLeft);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryLeft);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            updateCell(m, n + 1);

                        } else {                                //cell to the left is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Left);
                            setNextDirection(null);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Left);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(cellSize + midPoint);
                            pm.setY1(midPoint);

                            move(Directions.Left);

                        }

                    } else if (pm.isField1OnlyField()) {        //can possibly turn

                        int m = pm.getM1();
                        int n = pm.getN1() - 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the left is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryLeft);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryLeft);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            updateCell(m, n + 1);

                        } else {                                //cell to the left is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Left);
                            setNextDirection(null);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Left);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(cellSize + midPoint);
                            pm.setY0(midPoint);

                            move(Directions.Left);

                        }

                    } else {        //PacMan in between fields

                        if (currentDirection == Directions.Up)
                            moveUp();
                        else
                            moveDown();

                    }

                }

                //no action needed if stationary left

            } else if (nextDirection == Directions.Right) {     //USER DECIDED TO MOVE RIGHT

                if (currentDirection == Directions.StationaryLeft || currentDirection == Directions.StationaryUp || currentDirection == Directions.StationaryDown) {

                    setNextDirection(null);

                    if (pm.isField0OnlyField()) {

                        int m = pm.getM0();
                        int n = pm.getN0() + 1;

                        //checking whether the field on the right is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryRight);
                            setCurrentDirection(Directions.StationaryRight);
                            updateCell(m, n - 1);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Right);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Right);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(-cellSize + midPoint);
                            pm.setY1(midPoint);

                            move(Directions.Right);

                        }

                    } else if (pm.isField1OnlyField()) {

                        int m = pm.getM1();
                        int n = pm.getN1() + 1;

                        //checking whether the field on the right is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryRight);
                            setCurrentDirection(Directions.StationaryRight);
                            updateCell(m, n - 1);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Right);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Right);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(-cellSize + midPoint);
                            pm.setY0(midPoint);

                            move(Directions.Right);

                        }

                    }

                } else if (currentDirection == Directions.Left || currentDirection == Directions.Right) {

                    if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM0();
                        int n = pm.getN0() + 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the right is a wall;

                            if (pm.getX0() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setX0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryRight);
                                pm.setDirections(Directions.StationaryRight);
                                updateCell(m, n - 1);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Right);

                            }

                        } else {                                //cell to the right is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int x0 = pm.getX0();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (x0 > startPointOfMidPoint && pm.isField0OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField0OnlyField(false);
                                pm.setM1(m, false, true);
                                pm.setN1(n, true, false);
                                pm.setX1(-cellSize + x0);
                                pm.setY1(startPointOfMidPoint);

                            }

                            move(Directions.Right);

                        }


                    } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM1();
                        int n = pm.getN1() + 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the right is a wall;

                            if (pm.getX1() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setX1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryRight);
                                pm.setDirections(Directions.StationaryRight);
                                updateCell(m, n - 1);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Right);

                            }

                        } else {                                //cell to the right is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int x1 = pm.getX1();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (x1 > startPointOfMidPoint && pm.isField1OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField1OnlyField(false);
                                pm.setM0(m, false, true);
                                pm.setN0(n, true, false);
                                pm.setX0(-cellSize + x1);
                                pm.setY0(startPointOfMidPoint);

                            }

                            move(Directions.Right);

                        }

                    } else {        //PacMan in between fields

                        setCurrentDirection(Directions.Right);
                        pm.setDirections(Directions.Right);
                        move(Directions.Right);

                        int n0 = pm.getN0();
                        int n1 = pm.getN1();

                        if (n0 > n1) {       //Cell0 is on the right

                            if (pm.getX1() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell1

                                pm.setField0OnlyField(true);
                                notifyCellOfPmPresence(pm.getM1(), n1, false);

                            }

                        } else {        //Cell1 is on the right

                            if (pm.getX0() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell0

                                pm.setField1OnlyField(true);
                                notifyCellOfPmPresence(pm.getM0(), n0, false);

                            }

                        }

                    }

                } else if (currentDirection == Directions.Down || currentDirection == Directions.Up) {

                    if (pm.isField0OnlyField()) {        //can possibly turn

                        int m = pm.getM0();
                        int n = pm.getN0() + 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the right is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryRight);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryRight);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            updateCell(m, n - 1);

                        } else {                                //cell to the right is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Right);
                            setNextDirection(null);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Right);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(-cellSize + midPoint);
                            pm.setY1(midPoint);

                            move(Directions.Right);

                        }

                    } else if (pm.isField1OnlyField()) {        //can possibly turn

                        int m = pm.getM1();
                        int n = pm.getN1() + 1;

                        if (gw.getCellAt(m, n).isWall()) {       //cell to the right is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryRight);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryRight);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            updateCell(m, n - 1);

                        } else {                                //cell to the right is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Right);
                            setNextDirection(null);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Right);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(-cellSize + midPoint);
                            pm.setY0(midPoint);

                            move(Directions.Right);

                        }

                    } else {        //PacMan in between fields

                        if (currentDirection == Directions.Up)
                            moveUp();
                        else
                            moveDown();

                    }

                }

                //no action needed if stationary right

            } else if (nextDirection == Directions.Down) {           //USER DECIDED TO MOVE DOWNWARDS

                if (currentDirection == Directions.StationaryLeft || currentDirection == Directions.StationaryRight || currentDirection == Directions.StationaryUp) {

                    setNextDirection(null);

                    if (pm.isField0OnlyField()) {

                        int m = pm.getM0() + 1;
                        int n = pm.getN0();

                        //checking whether the field below is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryDown);
                            setCurrentDirection(Directions.StationaryDown);
                            updateCell(m - 1, n);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Down);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Down);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(midPoint);
                            pm.setY1(-cellSize + midPoint);

                            move(Directions.Down);

                        }

                    } else if (pm.isField1OnlyField()) {

                        int m = pm.getM1() + 1;
                        int n = pm.getN1();

                        //checking whether the field below is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryDown);
                            setCurrentDirection(Directions.StationaryDown);
                            updateCell(m - 1, n);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Down);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Down);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(midPoint);
                            pm.setY0(-cellSize + midPoint);

                            move(Directions.Down);

                        }

                    }

                } else if (currentDirection == Directions.Down || currentDirection == Directions.Up) {

                    if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM0() + 1;
                        int n = pm.getN0();

                        if (gw.getCellAt(m, n).isWall()) {       //cell below is a wall;

                            if (pm.getY0() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setY0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryDown);
                                pm.setDirections(Directions.StationaryDown);
                                updateCell(m - 1, n);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Down);

                            }

                        } else {                                //cell below is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int y0 = pm.getY0();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (y0 > startPointOfMidPoint && pm.isField0OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField0OnlyField(false);
                                pm.setM1(m, false, true);
                                pm.setN1(n, true, false);
                                pm.setX1(startPointOfMidPoint);
                                pm.setY1(-cellSize + y0);

                            }

                            move(Directions.Down);

                        }


                    } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM1() + 1;
                        int n = pm.getN1();

                        if (gw.getCellAt(m, n).isWall()) {       //cell below is a wall;

                            if (pm.getY1() >= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setY1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryDown);
                                pm.setDirections(Directions.StationaryDown);
                                updateCell(m - 1, n);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Down);

                            }

                        } else {                                //cell below is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int y1 = pm.getY1();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (y1 > startPointOfMidPoint && pm.isField1OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField1OnlyField(false);
                                pm.setM0(m, false, true);
                                pm.setN0(n, true, false);
                                pm.setX0(startPointOfMidPoint);
                                pm.setY0(-cellSize + y1);

                            }

                            move(Directions.Down);

                        }

                    } else {        //PacMan in between fields

                        setCurrentDirection(Directions.Down);
                        pm.setDirections(Directions.Down);
                        move(Directions.Down);

                        int m0 = pm.getM0();
                        int m1 = pm.getM1();

                        if (m0 > m1) {       //Cell0 is below

                            if (pm.getY1() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell1

                                pm.setField0OnlyField(true);
                                notifyCellOfPmPresence(m1, pm.getN1(), false);

                            }

                        } else {        //Cell1 is below

                            if (pm.getY0() >= gw.getCellSizeFromModel()) {     //PacMan fully cleared cell0

                                pm.setField1OnlyField(true);
                                notifyCellOfPmPresence(m0, pm.getN0(), false);

                            }

                        }

                    }

                } else if (currentDirection == Directions.Left || currentDirection == Directions.Right){

                    if (pm.isField0OnlyField()) {        //can possibly turn

                        int m = pm.getM0() + 1;
                        int n = pm.getN0();

                        if (gw.getCellAt(m, n).isWall()) {       //cell below is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryDown);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryDown);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            updateCell(m - 1, n);

                        } else {                                //cell below is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Down);
                            setNextDirection(null);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Down);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(midPoint);
                            pm.setY1(-cellSize + midPoint);

                            move(Directions.Down);

                        }

                    } else if (pm.isField1OnlyField()) {        //can possibly turn

                        int m = pm.getM1() + 1;
                        int n = pm.getN1();

                        if (gw.getCellAt(m, n).isWall()) {       //cell below is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryDown);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryDown);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            updateCell(m - 1, n);

                        } else {                                //cell below is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Down);
                            setNextDirection(null);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Down);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(midPoint);
                            pm.setY0(-cellSize + midPoint);

                            move(Directions.Down);

                        }

                    } else {        //PacMan in between fields

                        if (currentDirection == Directions.Left)
                            moveLeft();
                        else
                            moveRight();

                    }

                }

                //no action needed if stationary down

            } else if (nextDirection == Directions.Up) {           //USER DECIDED TO MOVE UPWARDS

                if (currentDirection == Directions.StationaryLeft || currentDirection == Directions.StationaryRight || currentDirection == Directions.StationaryDown) {

                    setNextDirection(null);

                    if (pm.isField0OnlyField()) {

                        int m = pm.getM0() - 1;
                        int n = pm.getN0();

                        //checking whether the field above is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryUp);
                            setCurrentDirection(Directions.StationaryUp);
                            updateCell(m + 1, n);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Up);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Up);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(midPoint);
                            pm.setY1(cellSize + midPoint);

                            move(Directions.Up);

                        }

                    } else if (pm.isField1OnlyField()) {

                        int m = pm.getM1() - 1;
                        int n = pm.getN1();

                        //checking whether the field above is a wall:
                        if (gw.getCellAt(m, n).isWall()) {

                            pm.setDirections(Directions.StationaryUp);
                            setCurrentDirection(Directions.StationaryUp);
                            updateCell(m + 1, n);

                        } else {

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Up);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Up);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(midPoint);
                            pm.setY0(cellSize + midPoint);

                            move(Directions.Up);

                        }

                    }

                } else if (currentDirection == Directions.Down || currentDirection == Directions.Up) {

                    if (pm.isField0OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM0() - 1;
                        int n = pm.getN0();

                        if (gw.getCellAt(m, n).isWall()) {       //cell above is a wall;

                            if (pm.getY0() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setY0((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryUp);
                                pm.setDirections(Directions.StationaryUp);
                                updateCell(m + 1, n);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Up);

                            }

                        } else {                                //cell above is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int y0 = pm.getY0();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (y0 < startPointOfMidPoint && pm.isField0OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField0OnlyField(false);
                                pm.setM1(m, false, true);
                                pm.setN1(n, true, false);
                                pm.setX1(startPointOfMidPoint);
                                pm.setY1(cellSize + y0);

                            }

                            move(Directions.Up);

                        }


                    } else if (pm.isField1OnlyField()) {        //there is a potential need to generate the next cell that is to be visited

                        int m = pm.getM1() - 1;
                        int n = pm.getN1();

                        if (gw.getCellAt(m, n).isWall()) {       //cell above is a wall;

                            if (pm.getY1() <= (gw.getCellSizeFromModel() - pm.getSideLength()) / 2) {   //PacMan moved past (or is on) the midpoint of the cell

                                pm.setY1((gw.getCellSizeFromModel() - pm.getSideLength()) / 2);

                                setCurrentDirection(Directions.StationaryUp);
                                pm.setDirections(Directions.StationaryUp);
                                updateCell(m + 1, n);

                            } else {        //PacMan has not yet cleared half of the field:

                                move(Directions.Up);

                            }

                        } else {                                //cell above is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int y1 = pm.getY1();
                            int pmSize = pm.getSideLength();
                            int startPointOfMidPoint = (cellSize - pmSize) / 2;

                            if (y1 < startPointOfMidPoint && pm.isField1OnlyField()) {   //PacMan moved past the midpoint of the cell

                                pm.setField1OnlyField(false);
                                pm.setM0(m, false, true);
                                pm.setN0(n, true, false);
                                pm.setX0(startPointOfMidPoint);
                                pm.setY0(cellSize + y1);

                            }

                            move(Directions.Up);

                        }

                    } else {        //PacMan in between fields

                        setCurrentDirection(Directions.Up);
                        pm.setDirections(Directions.Up);
                        move(Directions.Up);

                        int m0 = pm.getM0();
                        int m1 = pm.getM1();

                        if (m0 > m1) {       //Cell0 is below

                            if (pm.getY0() <= -pm.getSideLength()) {     //PacMan fully cleared cell0

                                pm.setField1OnlyField(true);
                                notifyCellOfPmPresence(m0, pm.getN0(), false);

                            }

                        } else {        //Cell1 is below

                            if (pm.getY1() <= -pm.getSideLength()) {     //PacMan fully cleared cell1

                                pm.setField0OnlyField(true);
                                notifyCellOfPmPresence(m1, pm.getN1(), false);

                            }

                        }

                    }

                } else if (currentDirection == Directions.Left || currentDirection == Directions.Right){

                    if (pm.isField0OnlyField()) {        //can possibly turn

                        int m = pm.getM0() - 1;
                        int n = pm.getN0();

                        if (gw.getCellAt(m, n).isWall()) {       //cell above is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryUp);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryUp);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            updateCell(m + 1, n);

                        } else {                                //cell above is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Up);
                            setNextDirection(null);

                            pm.setField0OnlyField(false);
                            pm.setDirections(Directions.Up);
                            pm.setX0(midPoint);
                            pm.setY0(midPoint);
                            pm.setM1(m, false, true);
                            pm.setN1(n, true, false);
                            pm.setX1(midPoint);
                            pm.setY1(cellSize + midPoint);

                            move(Directions.Up);

                        }

                    } else if (pm.isField1OnlyField()) {        //can possibly turn

                        int m = pm.getM1() - 1;
                        int n = pm.getN1();

                        if (gw.getCellAt(m, n).isWall()) {       //cell above is a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.StationaryUp);
                            setNextDirection(null);

                            pm.setDirections(Directions.StationaryUp);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            updateCell(m + 1, n);

                        } else {                                //cell above is not a wall;

                            int cellSize = gw.getCellSizeFromModel();
                            int pmSize = pm.getSideLength();
                            int midPoint = (cellSize - pmSize) / 2;

                            setCurrentDirection(Directions.Up);
                            setNextDirection(null);

                            pm.setField1OnlyField(false);
                            pm.setDirections(Directions.Up);
                            pm.setX1(midPoint);
                            pm.setY1(midPoint);
                            pm.setM0(m, false, true);
                            pm.setN0(n, true, false);
                            pm.setX0(midPoint);
                            pm.setY0(cellSize + midPoint);

                            move(Directions.Up);

                        }

                    } else {        //PacMan in between fields

                        if (currentDirection == Directions.Left)
                            moveLeft();
                        else
                            moveRight();

                    }

                }

                //no action needed if stationary up

            }

        }

    }

    public void stopRunning() {

        keepRunning = false;

    }

}
