public class ShiftLeftThread extends Thread{

    private static final int stepLength = 2;
    private static final long sleepForMS = 10;
    private boolean keepRunning;
    private final MenuWindow hostPanel;

    public ShiftLeftThread(MenuWindow hostPanel){

        keepRunning = true;
        this.hostPanel = hostPanel;

    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            hostPanel.shiftLeft(stepLength);

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
