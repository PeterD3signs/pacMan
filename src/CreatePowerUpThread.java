public class CreatePowerUpThread extends Thread{

    private static long sleepForMS;
    private boolean keepRunning;
    private final GameWindow gw;
    private final Ghost ghost;
    private boolean stopDrop;

    public CreatePowerUpThread(GameWindow gw, Ghost ghost){

        sleepForMS = 5000;
        this.gw = gw;
        this.ghost = ghost;
        keepRunning = true;
        stopDrop = false;

    }

    public void setStopDrop(boolean stopDrop){
        this.stopDrop = stopDrop;
    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            int a = (int)Math.floor(Math.random() * 4 + 1);     //25%

            if (a == 1 && !ghost.isScared() && !ghost.isEaten() && !stopDrop){

                if (ghost.isField0OnlyField()){

                    gw.createPowerUp(ghost.getM0(), ghost.getN0());

                } else if (ghost.isField1OnlyField()){

                    gw.createPowerUp(ghost.getM1(), ghost.getN1());

                } else {

                    if (ghost.getDirection() == Directions.Left){

                        if (ghost.getN0() > ghost.getN1())
                            gw.createPowerUp(ghost.getM0(), ghost.getN0());
                        else
                            gw.createPowerUp(ghost.getM1(), ghost.getN1());

                    } else if (ghost.getDirection() == Directions.Right){

                        if (ghost.getN0() > ghost.getN1())
                            gw.createPowerUp(ghost.getM1(), ghost.getN1());
                        else
                            gw.createPowerUp(ghost.getM0(), ghost.getN0());

                    } else if (ghost.getDirection() == Directions.Up){

                        if (ghost.getM0() > ghost.getM1())
                            gw.createPowerUp(ghost.getM0(), ghost.getN0());
                        else
                            gw.createPowerUp(ghost.getM1(), ghost.getN1());

                    } else {

                        if (ghost.getM0() > ghost.getM1())
                            gw.createPowerUp(ghost.getM1(), ghost.getN1());
                        else
                            gw.createPowerUp(ghost.getM0(), ghost.getN0());

                    }

                }

            }

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
