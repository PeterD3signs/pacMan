import java.awt.*;

public class PacMan {

    //ALWAYS CLOSE MOUTH PERC THREAD.

    private Directions directions;
    private Color color;
    private int mouthClosedPerc;
    private int sideLength;
    private int m0;      //board coordinates
    private int n0;
    private int x0;      //field coordinates
    private int y0;
    private int m1;
    private int n1;
    private int x1;
    private int y1;
    private boolean isField0OnlyField;
    private boolean isField1OnlyField;
    private final MouthPercThread mpt;
    private final UpdateCells uc;
    private boolean immortal;

    public PacMan(Directions directions, Color color, int mouthClosedPerc, int sideLength, UpdateCells uc) {
        this.directions = directions;
        this.color = color;
        this.mouthClosedPerc = mouthClosedPerc;
        this.sideLength = sideLength;
        this.uc = uc;
        immortal = false;

        mpt = new MouthPercThread(50, true, this);
        mpt.start();
    }

    public Directions getDirections() {
        return directions;
    }

    public int getMouthClosedPerc() {
        return mouthClosedPerc;
    }

    public int getSideLength() {
        return sideLength;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public void setMouthClosedPerc(int mouthClosedPerc) {
        this.mouthClosedPerc = mouthClosedPerc;
        uc.updateCells(m0, n0, m1, n1);
    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getM0() {
        return m0;
    }

    public void setM0(int m0, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfPmPresence(this.m0, this.n0, false);

        this.m0 = m0;

        if (notifyPresent)
            uc.notifyCellOfPmPresence(this.m0, this.n0, true);

    }

    public int getN0() {
        return n0;
    }

    public void setN0(int n0, boolean notifyPresent, boolean notifyAbsenty) {

        if (notifyAbsenty)
            uc.notifyCellOfPmPresence(this.m0, this.n0, false);

        this.n0 = n0;

        if (notifyPresent)
            uc.notifyCellOfPmPresence(this.m0, this.n0, true);

    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfPmPresence(this.m1, this.n1, false);

        this.m1 = m1;

        if (notifyPresent)
            uc.notifyCellOfPmPresence(this.m1, this.n1, true);

    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1, boolean notifyPresent, boolean notifyAbsent) {

        if (notifyAbsent)
            uc.notifyCellOfPmPresence(this.m1, this.n1, false);

        this.n1 = n1;

        if (notifyPresent)
            uc.notifyCellOfPmPresence(this.m1, this.n1, true);

    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
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
        mpt.stopRunning();
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }
}
