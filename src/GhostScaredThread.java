import java.awt.*;

public class GhostScaredThread extends Thread{

    private static long sleepForMS;
    private boolean keepRunning;
    private boolean scared;
    private boolean eaten;
    private final GameWindow gw;
    private final Ghost ghost;

    public GhostScaredThread(Ghost ghost, GameWindow gw){

        sleepForMS = 40;
        this.ghost = ghost;
        this.gw = gw;
        keepRunning = true;
        scared = false;
        eaten = false;

    }

    public void setScared(boolean scared){
        this.scared = scared;
    }

    public void setEaten(boolean eaten){
        this.eaten = eaten;
    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            if (scared){

                ghost.setColor(Color.WHITE);
                ghost.setMouthColor(Color.RED);
                ghost.genDirections(false, gw.getCells());

                int counter = 0;

                while (scared && keepRunning){

                    try{
                        sleep(sleepForMS);
                    } catch (InterruptedException ex){
                        ExceptionLog.addError(ex.getMessage());
                    }

                    counter  = counter + 1;

                    if (counter >= 20){

                        counter = 0;

                        if (ghost.getColor() == Color.WHITE){

                            ghost.setColor(Color.BLUE);
                            ghost.setMouthColor(Color.WHITE);

                        } else {

                            ghost.setColor(Color.WHITE);
                            ghost.setMouthColor(Color.RED);

                        }

                    }

                }

                if (eaten){

                    gw.increaseScore( gw.getEatGhostReward() );
                    gw.incrementEatGhostReward();
                    ghost.checkForVisit(true);

                } else {

                    ghost.genDirections(false, gw.getCells());

                    if (ghost.getGhostName() == GhostNames.Blinky)
                        ghost.setColor(Color.RED);
                    else if (ghost.getGhostName() == GhostNames.Pinky)
                        ghost.setColor(Color.PINK);
                    else if (ghost.getGhostName() == GhostNames.Inky)
                        ghost.setColor(Color.CYAN);
                    else
                        ghost.setColor(Color.ORANGE);

                }

            }

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
