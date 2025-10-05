public class MouthPercThread extends Thread{

    private static final int changePerc = 4;
    private static final long sleepForMS = 15;
    private int mouthClosedPerc;
    private boolean keepRunning;
    private final PacMan pm;
    private boolean increment;

    public MouthPercThread(int currentClosedPerc, boolean increment, PacMan pm){

        mouthClosedPerc = currentClosedPerc;
        this.increment = increment;
        keepRunning = true;
        this.pm = pm;

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

            if( pm.getDirections() == Directions.Left || pm.getDirections() == Directions.Right || pm.getDirections() == Directions.Up ||pm.getDirections() == Directions.Down) {

                mouthClosedPerc = (increment ? mouthClosedPerc + changePerc : mouthClosedPerc - changePerc);
                pm.setMouthClosedPerc(mouthClosedPerc);

            }

        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
