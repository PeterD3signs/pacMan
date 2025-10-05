public class ApplyPowerUpThread extends Thread{

    private static long sleepForMS;
    private boolean keepRunning;
    private final GameWindow gw;
    private boolean applyNew;
    private boolean skipSelection;

    public ApplyPowerUpThread(GameWindow gw){

        sleepForMS = 40;
        this.gw = gw;

        keepRunning = true;
        applyNew = false;
        skipSelection = false;

    }

    public void applyNewPowerUp(){
        applyNew = true;
    }

    public void applyInvulnerability(){
        applyNew = true;
        skipSelection = true;
    }

    public void run(){

        while (keepRunning){

            try{
                sleep(sleepForMS);
            } catch (InterruptedException ex){
                ExceptionLog.addError(ex.getMessage());
            }

            if (applyNew){

                applyNew = false;
                long timeSlept = 0;
                int newPowerUp;

                if (skipSelection) {

                    newPowerUp = 4;
                    skipSelection = false;

                } else {

                    newPowerUp = (int) Math.floor(Math.random() * 5 + 1);
                    //0 - null, 1 - eatGhosts, 2 - freezeGhosts, 3 - triple the speed, 4 - invulnerability, 5 - 3x points

                }

                if (newPowerUp == 1){
                    gw.setGhostScared(true);
                    gw.setPowerUpGraphic(1);
                } else if (newPowerUp == 2){
                    gw.freezeGhosts(true);
                    gw.setPowerUpGraphic(2);
                } else if (newPowerUp == 3){
                    gw.setTripleSpeed(true);
                    gw.setPowerUpGraphic(3);
                } else if (newPowerUp == 4){
                    gw.setPmInvulnerable(true);
                    gw.setPowerUpGraphic(4);
                } else {
                    gw.setThreeTimesPoints(true);
                    gw.setPowerUpGraphic(5);
                }

                boolean[] changedForPeriod = new boolean[4];

                while (!applyNew && timeSlept < 10000){

                    try{
                        sleep(sleepForMS);
                    } catch (InterruptedException ex){
                        ExceptionLog.addError(ex.getMessage());
                    }

                    timeSlept += sleepForMS;

                    if (timeSlept > 8000 && timeSlept < 8500 && !changedForPeriod[0] || timeSlept >= 9000 && timeSlept < 9500 && !changedForPeriod[2]){

                        changedForPeriod[0] = true;
                        gw.setPowerUpGraphic(0);

                    } else if (timeSlept >= 8500 && timeSlept < 9000 && !changedForPeriod[1] || timeSlept >= 9500 && !changedForPeriod[3]){

                        changedForPeriod[0] = true;
                        if (newPowerUp == 1){
                            gw.setPowerUpGraphic(1);
                        } else if (newPowerUp == 2){
                            gw.setPowerUpGraphic(2);
                        } else if (newPowerUp == 3){
                            gw.setPowerUpGraphic(3);
                        } else if (newPowerUp == 4){
                            gw.setPowerUpGraphic(4);
                        } else {
                            gw.setPowerUpGraphic(5);
                        }


                    }

                }

                if (newPowerUp == 1){
                    gw.setGhostScared(false);
                    gw.setPowerUpGraphic(0);
                } else if (newPowerUp == 2){
                    gw.freezeGhosts(false);
                    gw.setPowerUpGraphic(0);
                } else if (newPowerUp == 3){
                    gw.setTripleSpeed(false);
                    gw.setPowerUpGraphic(0);
                } else if (newPowerUp == 4){
                    gw.setPmInvulnerable(false);
                    gw.setPowerUpGraphic(0);
                } else {
                    gw.setThreeTimesPoints(false);
                    gw.setPowerUpGraphic(0);
                }

            }


        }

    }

    public void stopRunning(){

        keepRunning = false;

    }

}
