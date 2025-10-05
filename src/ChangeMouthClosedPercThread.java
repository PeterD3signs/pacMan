public class ChangeMouthClosedPercThread extends Thread{

    private static final int changePerc = 2;
    private static final long sleepForMS = 5;
    private int mouthClosedPerc;
    private boolean keepRunning;
    private final MouthClosedPercPassing hostPanel;
    private boolean increment;

    public ChangeMouthClosedPercThread(int currentClosedPerc, boolean increment, MouthClosedPercPassing hostPanel){

        mouthClosedPerc = currentClosedPerc;
        this.increment = increment;
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

            if (mouthClosedPerc > 100 ){
                mouthClosedPerc = 100;
                increment = false;
            } else if (mouthClosedPerc < 0){
                mouthClosedPerc = 0;
                increment = true;
            }

            mouthClosedPerc = (increment ? mouthClosedPerc + changePerc : mouthClosedPerc - changePerc);

            hostPanel.setMouthClosedPerc(mouthClosedPerc);

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }


}
