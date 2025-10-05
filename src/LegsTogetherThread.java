public class LegsTogetherThread extends Thread{

    private final long sleepForMS;
    private boolean keepRunning;
    private final Ghost ghost;
    private boolean legsTogether;
    private boolean pause;

    public LegsTogetherThread(Ghost ghost, long sleepForMS){

        this.sleepForMS = sleepForMS;
        this.ghost = ghost;
        keepRunning = true;
        legsTogether = true;
        pause = false;

    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

    public void run(){

        while (keepRunning && !pause){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            legsTogether = !legsTogether;

            ghost.setLegsTogether(legsTogether);

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
