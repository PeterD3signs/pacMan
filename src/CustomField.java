import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;

public class CustomField extends JPanel {

    /*

    A filed may contain:
    - exclusively a wall
    or any mix of the things below:
    - a path (mandatory)
    - a power up / a point
    - pac man
    - a ghost
    - any mix of the above

    Each field is a square;

    Each field has an origin to help calculate where exactly the objects are.
    It lies half the side length to the left and half the side length down from the lower left corner of the field.
    In other words, the center of the field is at x,x - where x is the length of the field.


     */

    //general variables:
    private final boolean isWall;
    private boolean hasPacMan;
    private boolean hasPoint;
    private boolean hasPowerUp;
    private boolean powerUpVisible;
    private boolean hasBlinky;
    private boolean hasPinky;
    private boolean hasInky;
    private boolean hasClyde;
    private int preferredSideSize;
    private final int mIndex;
    private final int nIndex;


    private PacMan pm;

    private Ghost Blinky;

    private Ghost Pinky;

    private Ghost Inky;

    private Ghost Clyde;

    public CustomField(boolean isWall, Color bgColor, int preferredSideSize, int mIndex, int nIndex){

        this.isWall = isWall;
        hasPacMan = false;
        hasBlinky = false;
        hasPinky = false;
        hasInky = false;
        hasClyde = false;
        hasPoint = !isWall;
        hasPowerUp = false;
        this.preferredSideSize = preferredSideSize;
        this.mIndex = mIndex;
        this.nIndex = nIndex;

        this.setBackground( bgColor );
        this.setFocusable(false);
        this.setVisible(true);

        repaint();

    }

    public void setPoint(boolean hasPoint){
        this.hasPoint = hasPoint;
        repaint();
    }

    public void setHasPowerUp(boolean hasPowerUp){
        this.hasPowerUp = hasPowerUp;
        repaint();
    }

    public void setPowerUpVisible(boolean visible){
        this.powerUpVisible = visible;
    }

    public boolean hasPowerUp(){
        return hasPowerUp;
    }

    public void paintPacMan(PacMan pm){

        hasPacMan = true;
        this.pm = pm;

        repaint();

    }

    public void paintGhost(Ghost ghost){

        if (ghost.getGhostName() == GhostNames.Blinky){

            hasBlinky = true;
            Blinky = ghost;

        } else if (ghost.getGhostName() == GhostNames.Pinky) {

            hasPinky = true;
            Pinky = ghost;

        } else if (ghost.getGhostName() == GhostNames.Inky) {

            hasInky = true;
            Inky = ghost;

        } else {

            hasClyde = true;
            Clyde = ghost;

        }

        repaint();

    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(preferredSideSize, preferredSideSize);

    }

    @Override
    protected void paintComponent (Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (hasPoint){

            int pointSize = preferredSideSize / 4;

            g2d.setPaint(Color.white);
            g2d.fillOval( preferredSideSize / 2 - pointSize / 2, preferredSideSize / 2 - pointSize / 2, pointSize, pointSize);

        }

        if (hasPowerUp && powerUpVisible){

            int powerUpSize = preferredSideSize / 2;

            g2d.setPaint(Color.green);
            g2d.fillOval( preferredSideSize / 2 - powerUpSize / 2, preferredSideSize / 2 - powerUpSize / 2, powerUpSize, powerUpSize);

        }

        if (hasBlinky){

            Area[] area = GhostArea.getArea( Blinky, isThisField0Ghost(GhostNames.Blinky) );

            if (area[0] != null) {
                g2d.setPaint(Blinky.getColor());
                g2d.fill(area[0]);
            }

            if (area[2] == null){     //if ghost is scared

                g2d.setPaint(Blinky.getMouthColor());
                g2d.fill( area[1] );

            } else {

                g2d.setPaint(Color.WHITE);
                g2d.fill( area[1] );

                g2d.setPaint(Color.BLACK);
                g2d.fill( area[2] );

            }

        }

        if (hasPinky){

            Area[] area = GhostArea.getArea( Pinky, isThisField0Ghost(GhostNames.Pinky) );

            if (area[0] != null) {
                g2d.setPaint(Pinky.getColor());
                g2d.fill(area[0]);
            }

            if (area[2] == null){     //if ghost is scared

                g2d.setPaint(Pinky.getMouthColor());
                g2d.fill( area[1] );

            } else {

                g2d.setPaint(Color.WHITE);
                g2d.fill( area[1] );

                g2d.setPaint(Color.BLACK);
                g2d.fill( area[2] );

            }

        }

        if (hasInky){

            Area[] area = GhostArea.getArea( Inky, isThisField0Ghost(GhostNames.Inky) );


            if (area[0] != null) {
                g2d.setPaint(Inky.getColor());
                g2d.fill(area[0]);
            }

            if (area[2] == null){     //if ghost is scared

                g2d.setPaint(Inky.getMouthColor());
                g2d.fill( area[1] );

            } else {

                g2d.setPaint(Color.WHITE);
                g2d.fill( area[1] );

                g2d.setPaint(Color.BLACK);
                g2d.fill( area[2] );

            }

        }

        if (hasClyde){

            Area[] area = GhostArea.getArea( Clyde, isThisField0Ghost(GhostNames.Clyde) );

            if (area[0] != null) {
                g2d.setPaint(Clyde.getColor());
                g2d.fill(area[0]);
            }

            if (area[2] == null){     //if ghost is scared

                g2d.setPaint(Clyde.getMouthColor());
                g2d.fill( area[1] );

            } else {

                g2d.setPaint(Color.WHITE);
                g2d.fill( area[1] );

                g2d.setPaint(Color.BLACK);
                g2d.fill( area[2] );

            }

        }

        if (hasPacMan){

            Area PacMan = PacManArea.getArea(pm, isThisField0PM());

            g2d.setPaint(pm.getColor());
            g2d.fill( PacMan );

        }

        //model.cellUpdated(mIndex, nIndex);

    }

    public void updateContents(){
        repaint();
    }

    public void rescale(int newPreferredSideSize){

        preferredSideSize = newPreferredSideSize;
        repaint();

    }

    public boolean isWall(){
        return isWall;
    }

    public boolean hasPacMan() {
        return hasPacMan;
    }

    public void setHasPacMan(boolean hasPacMan) {
        this.hasPacMan = hasPacMan;
    }

    public void setHasBlinky(boolean hasBlinky) {
        this.hasBlinky = hasBlinky;
    }

    public void setHasPinky(boolean hasPinky) {
        this.hasPinky = hasPinky;
    }

    public void setHasInky(boolean hasInky) {
        this.hasInky = hasInky;
    }

    public void setHasClyde(boolean hasClyde) {
        this.hasClyde = hasClyde;
    }

    public void setPm(PacMan pm) {
        this.pm = pm;
    }

    public void setBlinky(Ghost blinky) {
        Blinky = blinky;
    }

    public void setPinky(Ghost pinky) {
        Pinky = pinky;
    }

    public void setInky(Ghost inky) {
        Inky = inky;
    }

    public void setClyde(Ghost clyde) {
        Clyde = clyde;
    }

    private boolean isThisField0PM(){

        return (pm.getM0() == mIndex && pm.getN0() == nIndex);

    }

    private boolean isThisField0Ghost(GhostNames ghostName){

        boolean isField0 = false;

        if (ghostName == GhostNames.Blinky && Blinky.getM0() == mIndex && Blinky.getN0() == nIndex)
            isField0 = true;
        else if (ghostName == GhostNames.Pinky && Pinky.getM0() == mIndex && Pinky.getN0() == nIndex)
            isField0 = true;
        else if (ghostName == GhostNames.Inky && Inky.getM0() == mIndex && Inky.getN0() == nIndex)
            isField0 = true;
        else if (ghostName == GhostNames.Clyde && Clyde.getM0() == mIndex && Clyde.getN0() == nIndex)
            isField0 = true;

        return isField0;
    }

    public boolean hasPoint() {
        return hasPoint;
    }

}
