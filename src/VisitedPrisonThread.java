import java.awt.*;

public class VisitedPrisonThread extends Thread{

    private static long sleepForMS;
    private boolean keepRunning;
    private final Ghost ghost;
    private boolean checkForVisit;

    public VisitedPrisonThread(Ghost ghost){

        sleepForMS = 40;
        this.ghost = ghost;
        keepRunning = true;
        checkForVisit = false;

    }

    public void checkForVisit(boolean check){
        checkForVisit = check;
    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            if (checkForVisit){

                while (checkForVisit && keepRunning){

                    try{
                        sleep(sleepForMS);
                    } catch (InterruptedException ex){
                        ExceptionLog.addError(ex.getMessage());
                    }

                }

                if (ghost.getGhostName() == GhostNames.Blinky)
                    ghost.setColor(Color.RED);
                else if (ghost.getGhostName() == GhostNames.Pinky)
                    ghost.setColor(Color.PINK);
                else if (ghost.getGhostName() == GhostNames.Inky)
                    ghost.setColor(Color.CYAN);
                else
                    ghost.setColor(Color.ORANGE);

                ghost.setEaten(false);

            }



        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
