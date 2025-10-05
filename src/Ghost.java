import java.awt.*;
import java.util.LinkedList;

public class Ghost {

    //ALWAYS CLOSE LEGS TOGETHER THREAD.

    private final GhostNames ghostName;
    private Color color;
    private Color mouthColor;
    private int sideLength;
    private boolean legsTogether;
    private Directions direction;
    private int m0;      //board coordinates;
    private int n0;
    private int x0;      //field coordinates;
    private int y0;
    private int m1;
    private int n1;
    private int x1;
    private int y1;
    private boolean eaten;
    private boolean scared;
    private boolean isField0OnlyField;
    private boolean isField1OnlyField;
    private final GhostScaredThread gst;
    private final LegsTogetherThread ltt;
    private final CreatePowerUpThread cput;
    private final VisitedPrisonThread vpt;
    private final UpdateCells uc;
    private int[][] valueMap;

    public Ghost(GhostNames ghostName, Color color, Color mouthColor, int sideLength, boolean legsTogether, Directions direction, boolean eaten, boolean scared, UpdateCells uc){

        this.ghostName = ghostName;
        this.color = color;
        this.mouthColor = mouthColor;
        this.sideLength = sideLength;
        this.legsTogether = legsTogether;
        this.direction = direction;
        this.eaten = eaten;
        this.scared = scared;
        this.uc = uc;

        vpt = new VisitedPrisonThread(this);
        cput = new CreatePowerUpThread((GameWindow)uc , this);
        gst = new GhostScaredThread(this, (GameWindow)uc );

        ltt = new LegsTogetherThread(this, (long)(Math.floor(Math.random() *(2000 - 1000 + 1) + 1000)) / 10 * 10);
        ltt.start();

    }

    private int[][] prepareValueMap(CustomField[][] cells){

        //-1 == ungenerated
        //-2 == wall
        int[][] valueMap = new int[cells.length][cells[0].length];

        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                valueMap[i][j] = (cells[i][j].isWall() ? -2 : -1);

        return valueMap;

    }

    public void genDirections(boolean prison, CustomField[][] cells){

        int[][] valueMap = prepareValueMap(cells);
        LinkedList<Integer[]> cellsToGen = new LinkedList<>();

        int X;
        int Y;

        for (int i = 0; i < valueMap.length; i++)
            for (int j = 0; j < valueMap[i].length; j++) {

                if (valueMap[i][j] == -1) {
                    cellsToGen.add(new Integer[]{i, j});
                }

            }

        int height = valueMap.length - 2;
        int width = valueMap[0].length - 2;

        if (prison){        //go to prison

            if (ghostName == GhostNames.Pinky) {

                Y = height / 2 + 1;
                X = width / 2 + 2;

            }else if (ghostName == GhostNames.Inky) {

                Y = height / 2 + 1;
                X = width / 2 + 1;

            }else if (ghostName == GhostNames.Blinky) {

                Y = height / 2 + 1;
                X = width / 2;

            }else {

                Y = height / 2;
                X = width / 2 + 1;
            }

            valueMap[Y][X] = 0;

        } else {        //go somewhere else

            boolean cellFound = false;
            while (!cellFound) {

                int goToWidth = (int) (Math.floor(Math.random() * (width) + 1));
                int goToHeight = (int) (Math.floor(Math.random() * (height) + 1));

                if (
                        valueMap[goToHeight][goToWidth] != -2 &&
                                (goToHeight != height / 2 + 1 && goToHeight != height / 2 && goToHeight != height / 2 - 1 && goToHeight != height / 2 - 2 ||
                                        goToHeight == height / 2 && goToWidth != width / 2 + 1 ||
                                        goToHeight == height / 2 + 1 && goToWidth != width / 2 && goToWidth != width / 2 + 1 && goToWidth != width / 2 + 2 ||
                                        goToHeight == height / 2 - 1 && goToWidth != width / 2 && goToWidth != width / 2 + 1 && goToWidth != width / 2 + 2 ||
                                        goToHeight == height / 2 - 2 && goToWidth != width / 2 + 1
                                )
                ) {

                    valueMap[goToHeight][goToWidth] = 0;
                    cellFound = true;

                }
            }

        }

        //plotting the value map:
        while (!cellsToGen.isEmpty() ){//&& j < cellsToGen.size() + 3

            for (int i = 0; i < cellsToGen.size(); i++){

                Integer[] indexes = cellsToGen.get(i);

                if (valueMap[indexes[0]][indexes[1]] != -1 ){
                    cellsToGen.remove(i);
                    i--;
                } else {

                    int minimum = Math.max(valueMap[indexes[0] - 1][indexes[1]], -1);

                    if (valueMap[indexes[0] + 1][indexes[1]] > -1){

                        if (minimum == -1)
                            minimum = valueMap[indexes[0] + 1][indexes[1]];
                        else
                            minimum = Math.min(minimum, valueMap[indexes[0] + 1][indexes[1]]);

                    }

                    if (valueMap[indexes[0]][indexes[1] + 1] > -1){

                        if (minimum == -1)
                            minimum = valueMap[indexes[0]][indexes[1] + 1];
                        else
                            minimum = Math.min(minimum, valueMap[indexes[0]][indexes[1] + 1]);

                    }

                    if (valueMap[indexes[0]][indexes[1] - 1] > -1){

                        if (minimum == -1)
                            minimum = valueMap[indexes[0]][indexes[1] - 1];
                        else
                            minimum = Math.min(minimum, valueMap[indexes[0]][indexes[1] - 1]);

                    }

                    if (minimum != -1){

                        valueMap[indexes[0]][indexes[1]] = minimum + 1;
                        cellsToGen.remove(i);
                        i--;

                    }
                }

            }

        }

        this.valueMap = valueMap;

    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setMouthColor(Color mouthColor) {
        this.mouthColor = mouthColor;
    }

    public void setLegsTogether(boolean legsTogether) {
        this.legsTogether = legsTogether;
        uc.updateCells(m0, n0, m1, n1);
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setM1(int m1, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m1, this.n1, false);

        this.m1 = m1;

        if (notifyPresent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m1, this.n1, true);

    }

    public void setN1(int n1, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m1, this.n1, false);

        this.n1 = n1;

        if (notifyPresent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m1, this.n1, true);

    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public void setM0(int m0, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m0, this.n0, false);

        this.m0 = m0;

        if (notifyPresent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m0, this.n0, true);

    }

    public void setN0(int n0, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m0, this.n0, false);

        this.n0 = n0;

        if (notifyPresent)
            uc.notifyCellOfGhostPresence(this.getGhostName(), this.m0, this.n0, true);


    }

    public void setEaten(boolean eaten) {

        this.eaten = eaten;
        gst.setEaten(eaten);

    }

    public void setScared(boolean scared) {

        this.scared = scared;
        gst.setScared(scared);

    }

    public Color getColor(){
        return color;
    }

    public Color getMouthColor() {
        return mouthColor;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getN1() {
        return n1;
    }

    public int getM1() {
        return m1;
    }

    public int getX0() {
        return x0;
    }

    public int getY0() {
        return y0;
    }

    public int getN0() {
        return n0;
    }

    public int getM0() {
        return m0;
    }

    public GhostNames getGhostName() {
        return ghostName;
    }

    public Directions getDirection() {
        return direction;
    }

    public int getSideLength() {
        return sideLength;
    }

    public boolean isLegsTogether() {
        return legsTogether;
    }

    public boolean isEaten() {
        return eaten;
    }

    public boolean isScared() {
        return scared;
    }

    public boolean isField0OnlyField() {
        return isField0OnlyField;
    }

    public void setField0OnlyField(boolean field0OnlyField) {
        isField0OnlyField = field0OnlyField;
    }

    public boolean isField1OnlyField() {
        return isField1OnlyField;
    }

    public void setField1OnlyField(boolean field1OnlyField) {
        isField1OnlyField = field1OnlyField;
    }

    public void stopThread(){
        ltt.stopRunning();
        cput.stopRunning();
        gst.stopRunning();
        vpt.stopRunning();
    }

    public void startThread(){
        cput.start();
        gst.start();
        vpt.start();
    }

    public int[][] getValueMap(){
        return valueMap;
    }

    public void checkForVisit(boolean check){
        vpt.checkForVisit(check);
    }

    public void setPause(boolean pause){
        ltt.setPause(pause);
        cput.setStopDrop(pause);
    }
}
