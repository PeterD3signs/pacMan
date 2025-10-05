public class PowerUpFlashThread extends Thread{

    private final long sleepForMS;
    private boolean keepRunning;
    private boolean visible;
    private final  GameWindow gw;

    public PowerUpFlashThread(GameWindow gw){

        this.sleepForMS = 500;
        this.gw = gw;
        keepRunning = true;
        visible = false;

    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            gw.setPowerUpsVisibility(visible);
            visible = !visible;

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
