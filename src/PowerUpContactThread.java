public class PowerUpContactThread extends Thread{

    private static long sleepForMS;
    private boolean keepRunning;
    private final  PacMan pm;
    private final  GameWindow gw;
    private int increaseAmount;

    public PowerUpContactThread(PacMan pm, GameWindow gw){

        sleepForMS = 40;
        this.pm = pm;
        this.gw = gw;
        keepRunning = true;
        increaseAmount = 50;

    }

    public void setIncreaseAmount(int increaseAmount){
        this.increaseAmount = increaseAmount;
    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }


            //Adding points and activating power ups:
            if (pm.isField0OnlyField()) {

                int m = pm.getM0();
                int n = pm.getN0();

                if (gw.getCellAt(m, n).hasPowerUp()) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(m, n);
                    gw.applyPowerUp();

                }

            }else if (pm.isField1OnlyField()) {

                int m = pm.getM1();
                int n = pm.getN1();

                if (gw.getCellAt(m, n).hasPowerUp()) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(m, n);
                    gw.applyPowerUp();

                }

            } else {

                int m0 = pm.getM0();
                int n0 = pm.getN0();

                int m1 = pm.getM1();
                int n1 = pm.getN1();

                Directions pmDir = pm.getDirections();

                CustomField f0 = gw.getCellAt(m0, n0);
                CustomField f1 = gw.getCellAt(m1, n1);

                int cellSize = gw.getCellSizeFromModel();

                if (pmDir == Directions.Down && m0 > m1 && f0.hasPowerUp() && pm.getY0() > -cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM0(), pm.getN0());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Down && m1 > m0 && f1.hasPowerUp() && pm.getY1() > -cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM1(), pm.getN1());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Up && m0 > m1 && f1.hasPowerUp() && pm.getY1() < cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM1(), pm.getN1());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Up && m1 > m0 && f0.hasPowerUp() && pm.getY0() < cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM0(), pm.getN0());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Left && m1 > m0 && f0.hasPowerUp() && pm.getX0() < cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM0(), pm.getN0());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Left && m0 > m1 && f1.hasPowerUp() && pm.getX1() < cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM1(), pm.getN1());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Right && m0 > m1 && f0.hasPowerUp() && pm.getX0() > -cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM0(), pm.getN0());
                    gw.applyPowerUp();

                } else if (pmDir == Directions.Right && m1 > m0 && f1.hasPowerUp() && pm.getX1() > -cellSize / 2) {

                    gw.increaseScore(increaseAmount);
                    gw.deletePowerUp(pm.getM1(), pm.getN1());
                    gw.applyPowerUp();

                }

            }

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
