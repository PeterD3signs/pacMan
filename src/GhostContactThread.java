
public class GhostContactThread extends Thread{

    private static long sleepForMS;
    private boolean keepRunning;
    private final PacMan pm;
    private final Ghost Blinky;
    private final Ghost Pinky;
    private final Ghost Inky;
    private final Ghost Clyde;
    private final GameWindow gw;

    public GhostContactThread(PacMan pm, Ghost Blinky, Ghost Pinky, Ghost Inky, Ghost Clyde, GameWindow gw){

        sleepForMS = 40;
        this.pm = pm;
        this.gw = gw;
        this.Blinky = Blinky;
        this.Pinky = Pinky;
        this.Inky = Inky;
        this.Clyde = Clyde;
        keepRunning = true;

    }

    public boolean doesOverlap (int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){

        boolean overlaps = true;

        if ( (x3 > x2) || (y3 < y2) || (x1 > x4) || (y1 < y4) ){
            overlaps = false;
        }

        return overlaps;

    }

    private void updateCells (int m0, int n0, int m1, int n1){
        gw.updateCells(m0, n0, m1, n1);
    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            //plotting ghosts and PacMan on a huge grid and checking for overlap by assuming rectangular hit-boxes:
            //(checking using lower left corner 'a' and upper right corner 'b'

            /*
            calculating the X and Y of upper left corner for every creature, by using given method:

                if only on field0 -> use x0
                else if only on field1 -> use x1
                else if on both fields -> {
                     if n of both fields is the same (upwards/downwards direction) -> use min(x0, x1)
                     else ->{
                            if n0 < n1 -> use x0
                            else -> use x1
                     }
                }

                ...and adding n * cellSize

                Calculating Y respectively.

             */
            int cellSize = gw.getCellSizeFromModel();

            PacMan pm = this.pm;
            Ghost Blinky = this.Blinky;
            Ghost Pinky = this.Pinky;
            Ghost Inky = this.Inky;
            Ghost Clyde = this.Clyde;

            int PacManX = (pm.isField0OnlyField() ? pm.getX0() : (pm.isField1OnlyField() ? pm.getX1() : ( pm.getN0() == pm.getN1() ) ? Math.min(pm.getX0(), pm.getX1()) : ( pm.getN0() < pm.getN1() ? pm.getX0() : pm.getX1() ) ));
            int PacManY = (pm.isField0OnlyField() ? pm.getY0() : (pm.isField1OnlyField() ? pm.getY1() : ( pm.getM0() == pm.getM1() ) ? Math.min(pm.getY0(), pm.getY1()) : ( pm.getM0() < pm.getM1() ? pm.getY0() : pm.getY1() ) ));

            PacManX += ( (pm.isField0OnlyField() ? pm.getN0() : (pm.isField1OnlyField() ? pm.getN1() : Math.min(pm.getN0(), pm.getN1()))) - 1 ) * cellSize;
            PacManY += ( (pm.isField0OnlyField() ? pm.getM0() : (pm.isField1OnlyField() ? pm.getM1() : Math.min(pm.getM0(), pm.getM1()))) - 1 ) * cellSize;

            int PacManAx = PacManX;
            int PacManAy = PacManY + pm.getSideLength();

            int PacManBx = PacManX + pm.getSideLength();
            int PacManBy = PacManY;


            int BlinkyX = (Blinky.isField0OnlyField() ? Blinky.getX0() : (Blinky.isField1OnlyField() ? Blinky.getX1() : ( Blinky.getN0() == Blinky.getN1() ) ? Math.min(Blinky.getX0(), Blinky.getX1()) : ( Blinky.getN0() < Blinky.getN1() ? Blinky.getX0() : Blinky.getX1() ) ));
            int BlinkyY = (Blinky.isField0OnlyField() ? Blinky.getY0() : (Blinky.isField1OnlyField() ? Blinky.getY1() : ( Blinky.getM0() == Blinky.getM1() ) ? Math.min(Blinky.getY0(), Blinky.getY1()) : ( Blinky.getM0() < Blinky.getM1() ? Blinky.getY0() : Blinky.getY1() ) ));

            BlinkyX += ( (Blinky.isField0OnlyField() ? Blinky.getN0() : (Blinky.isField1OnlyField() ? Blinky.getN1() : Math.min(Blinky.getN0(), Blinky.getN1()))) - 1 ) * cellSize;
            BlinkyY += ( (Blinky.isField0OnlyField() ? Blinky.getM0() : (Blinky.isField1OnlyField() ? Blinky.getM1() : Math.min(Blinky.getM0(), Blinky.getM1()))) - 1 ) * cellSize;

            int BlinkyAx = BlinkyX;
            int BlinkyAy = BlinkyY + Blinky.getSideLength();

            int BlinkyBx = BlinkyX + Blinky.getSideLength();
            int BlinkyBy = BlinkyY;


            int PinkyX = (Pinky.isField0OnlyField() ? Pinky.getX0() : (Pinky.isField1OnlyField() ? Pinky.getX1() : ( Pinky.getN0() == Pinky.getN1() ) ? Math.min(Pinky.getX0(), Pinky.getX1()) : ( Pinky.getN0() < Pinky.getN1() ? Pinky.getX0() : Pinky.getX1() ) ));
            int PinkyY = (Pinky.isField0OnlyField() ? Pinky.getY0() : (Pinky.isField1OnlyField() ? Pinky.getY1() : ( Pinky.getM0() == Pinky.getM1() ) ? Math.min(Pinky.getY0(), Pinky.getY1()) : ( Pinky.getM0() < Pinky.getM1() ? Pinky.getY0() : Pinky.getY1() ) ));

            PinkyX += ( (Pinky.isField0OnlyField() ? Pinky.getN0() : (Pinky.isField1OnlyField() ? Pinky.getN1() : Math.min(Pinky.getN0(), Pinky.getN1()))) - 1 ) * cellSize;
            PinkyY += ( (Pinky.isField0OnlyField() ? Pinky.getM0() : (Pinky.isField1OnlyField() ? Pinky.getM1() : Math.min(Pinky.getM0(), Pinky.getM1()))) - 1 ) * cellSize;

            int PinkyAx = PinkyX;
            int PinkyAy = PinkyY + Pinky.getSideLength();

            int PinkyBx = PinkyX + Pinky.getSideLength();
            int PinkyBy = PinkyY;


            int InkyX = (Inky.isField0OnlyField() ? Inky.getX0() : (Inky.isField1OnlyField() ? Inky.getX1() : ( Inky.getN0() == Inky.getN1() ) ? Math.min(Inky.getX0(), Inky.getX1()) : ( Inky.getN0() < Inky.getN1() ? Inky.getX0() : Inky.getX1() ) ));
            int InkyY = (Inky.isField0OnlyField() ? Inky.getY0() : (Inky.isField1OnlyField() ? Inky.getY1() : ( Inky.getM0() == Inky.getM1() ) ? Math.min(Inky.getY0(), Inky.getY1()) : ( Inky.getM0() < Inky.getM1() ? Inky.getY0() : Inky.getY1() ) ));

            InkyX += ( (Inky.isField0OnlyField() ? Inky.getN0() : (Inky.isField1OnlyField() ? Inky.getN1() : Math.min(Inky.getN0(), Inky.getN1()))) - 1 ) * cellSize;
            InkyY += ( (Inky.isField0OnlyField() ? Inky.getM0() : (Inky.isField1OnlyField() ? Inky.getM1() : Math.min(Inky.getM0(), Inky.getM1()))) - 1 ) * cellSize;

            int InkyAx = InkyX;
            int InkyAy = InkyY + Inky.getSideLength();

            int InkyBx = InkyX + Inky.getSideLength();
            int InkyBy = InkyY;


            int ClydeX = (Clyde.isField0OnlyField() ? Clyde.getX0() : (Clyde.isField1OnlyField() ? Clyde.getX1() : ( Clyde.getN0() == Clyde.getN1() ) ? Math.min(Clyde.getX0(), Clyde.getX1()) : ( Clyde.getN0() < Clyde.getN1() ? Clyde.getX0() : Clyde.getX1() ) ));
            int ClydeY = (Clyde.isField0OnlyField() ? Clyde.getY0() : (Clyde.isField1OnlyField() ? Clyde.getY1() : ( Clyde.getM0() == Clyde.getM1() ) ? Math.min(Clyde.getY0(), Clyde.getY1()) : ( Clyde.getM0() < Clyde.getM1() ? Clyde.getY0() : Clyde.getY1() ) ));

            ClydeX += ( (Clyde.isField0OnlyField() ? Clyde.getN0() : (Clyde.isField1OnlyField() ? Clyde.getN1() : Math.min(Clyde.getN0(), Clyde.getN1()))) - 1 ) * cellSize;
            ClydeY += ( (Clyde.isField0OnlyField() ? Clyde.getM0() : (Clyde.isField1OnlyField() ? Clyde.getM1() : Math.min(Clyde.getM0(), Clyde.getM1()))) - 1 ) * cellSize;

            int ClydeAx = ClydeX;
            int ClydeAy = ClydeY + Clyde.getSideLength();

            int ClydeBx = ClydeX + Clyde.getSideLength();
            int ClydeBy = ClydeY;


            //checkig overlaping:
            if (doesOverlap(PacManAx, PacManAy, PacManBx, PacManBy, BlinkyAx, BlinkyAy, BlinkyBx, BlinkyBy)){

                //PacMan overlaps with Blinky

                if (Blinky.isScared()){

                    this.Blinky.setEaten(true);
                    this.Blinky.setScared(false);
                    this.Blinky.genDirections(true, gw.getCells());

                    if (Blinky.isField1OnlyField())
                        updateCells(Blinky.getM1(), Blinky.getN1(), -1, -1);
                    else if (Blinky.isField0OnlyField())
                        updateCells(Blinky.getM0(), Blinky.getN0(), -1, -1);
                    else
                        updateCells(Blinky.getM0(), Blinky.getN0(), Blinky.getM1(), Blinky.getN1());


                } else if (!Blinky.isEaten() && !pm.isImmortal()){

                    gw.death();

                }

            } else if (doesOverlap(PacManAx, PacManAy, PacManBx, PacManBy, PinkyAx, PinkyAy, PinkyBx, PinkyBy)){

                //PacMan overlaps with Pinky

                if (Pinky.isScared()){

                    this.Pinky.setEaten(true);
                    this.Pinky.setScared(false);
                    this.Pinky.genDirections(true, gw.getCells());

                    if (Pinky.isField1OnlyField())
                        updateCells(Pinky.getM1(), Pinky.getN1(), -1, -1);
                    else if (Pinky.isField0OnlyField())
                        updateCells(Pinky.getM0(), Pinky.getN0(), -1, -1);
                    else
                        updateCells(Pinky.getM0(), Pinky.getN0(), Pinky.getM1(), Pinky.getN1());

                } else if (!Pinky.isEaten() && !pm.isImmortal()){

                    gw.death();

                }

            } else if (doesOverlap(PacManAx, PacManAy, PacManBx, PacManBy, InkyAx, InkyAy, InkyBx, InkyBy)){

                //PacMan overlaps with Inky

                if (Inky.isScared()){

                    this.Inky.setEaten(true);
                    this.Inky.setScared(false);
                    this.Inky.genDirections(true, gw.getCells());

                    if (Inky.isField1OnlyField())
                        updateCells(Inky.getM1(), Inky.getN1(), -1, -1);
                    else if (Inky.isField0OnlyField())
                        updateCells(Inky.getM0(), Inky.getN0(), -1, -1);
                    else
                        updateCells(Inky.getM0(), Inky.getN0(), Inky.getM1(), Inky.getN1());

                } else if (!Inky.isEaten() && !pm.isImmortal()){

                    gw.death();

                }

            } else if (doesOverlap(PacManAx, PacManAy, PacManBx, PacManBy, ClydeAx, ClydeAy, ClydeBx, ClydeBy)){

                //PacMan overlaps with Clyde

                if (Clyde.isScared()){

                    this.Clyde.setEaten(true);
                    this.Clyde.setScared(false);
                    this.Clyde.genDirections(true, gw.getCells());

                    if (Clyde.isField1OnlyField())
                        updateCells(Clyde.getM1(), Clyde.getN1(), -1, -1);
                    else if (Clyde.isField0OnlyField())
                        updateCells(Clyde.getM0(), Clyde.getN0(), -1, -1);
                    else
                        updateCells(Clyde.getM0(), Clyde.getN0(), Clyde.getM1(), Clyde.getN1());

                } else if (!Clyde.isEaten() && !pm.isImmortal()){

                    gw.death();

                }

            }

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
