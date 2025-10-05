import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class PaintPacMan extends JComponent implements MouthClosedPercPassing {

    private final String Direction;
    private final Color Color;
    private int MouthClosedPerc;
    private int SideLength;
    private int EyeLength;
    private int X;
    private int Y;
    private int ahs;    //available horizontal space
    private int avs;    //available vertical space

    public PaintPacMan(Color Color, int SideLength, int EyeLength , int MouthClosedPerc, String Direction , int X, int Y, int ahs, int avs){

        this.Color = Color;
        this.SideLength = SideLength;
        this.EyeLength = EyeLength;
        this.MouthClosedPerc = MouthClosedPerc;
        this.Direction = Direction;
        this.X = X;
        this.Y = Y;
        this.ahs = ahs;
        this.avs = avs;


    }

    @Override
    public Dimension getPreferredSize() {

        int xDimension = X + SideLength;
        int yDimension = Y + SideLength;

        if (xDimension > ahs)
            xDimension = ahs;

        if (xDimension < 0)
            xDimension = 0;

        if (yDimension > avs)
            yDimension = avs;

        if (yDimension < 0)
            yDimension = 0;

        return new Dimension( xDimension, yDimension );

    }

    @Override
    protected void paintComponent (Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Area PacMan = new Area( new Ellipse2D.Double(X, Y, SideLength, SideLength) );
        Area mouth;
        Area eye;

        int mouthBottomX, mouthBottomY, mouthTopX, mouthTopY, eyeX, eyeY;

        if ( Direction.equals( Directions.Left.toString() ) || Direction.equals( Directions.StationaryLeft.toString() ) ){

            mouthTopX = X;
            mouthTopY = Y + SideLength / 2 * MouthClosedPerc / 100;

            mouthBottomX = X;
            mouthBottomY = Y + SideLength - SideLength / 2 * MouthClosedPerc / 100;

            eyeX = X + SideLength / 2 - EyeLength / 2;
            eyeY = Y + SideLength / 7;

        } else if ( Direction.equals( Directions.Right.toString() ) || Direction.equals( Directions.StationaryRight.toString() ) ){

            mouthTopX = X + SideLength;
            mouthTopY = Y + SideLength / 2 * MouthClosedPerc / 100;

            mouthBottomX = X + SideLength;
            mouthBottomY = Y + SideLength - SideLength / 2 * MouthClosedPerc / 100;

            eyeX = X + SideLength / 2 - EyeLength / 2;
            eyeY = Y + SideLength / 7;

        } else if ( Direction.equals( Directions.Up.toString() ) || Direction.equals( Directions.StationaryUp.toString() ) ){

            mouthTopX = X + SideLength - SideLength / 2 * MouthClosedPerc / 100;
            mouthTopY = Y;

            mouthBottomX = X + SideLength/2 * MouthClosedPerc / 100;
            mouthBottomY = Y;

            eyeX = X + SideLength * 5 / 7;
            eyeY = Y + SideLength / 2 - EyeLength / 2;

        } else {    //down

            mouthTopX = X + SideLength - SideLength / 2 * MouthClosedPerc / 100;
            mouthTopY = Y + SideLength;

            mouthBottomX = X + SideLength/2 * MouthClosedPerc / 100;
            mouthBottomY = Y + SideLength;

            eyeX = X + SideLength / 7;
            eyeY = Y + SideLength / 2 - EyeLength / 2;

        }

        mouth = new Area( new Polygon( new int[]{mouthTopX, mouthBottomX, X + SideLength / 2}, new int[]{mouthTopY, mouthBottomY, Y + SideLength / 2}, 3 ) );
        eye = new Area(new Ellipse2D.Double(eyeX, eyeY, EyeLength, EyeLength) );
        
        PacMan.subtract( mouth );
        PacMan.subtract( eye );

        g2d.setPaint(Color);
        g2d.fill(PacMan);

    }

    public void setPmX(int x) {
        X = x;
        repaint();
    }

    public void changeSize(int x, int y, int sideLength, int eyeLength, int ahs, int avs){

        this.SideLength = sideLength;
        this.EyeLength = eyeLength;
        X = x;
        Y = y;
        this.ahs = ahs;
        this.avs = avs;

        repaint();
    }

    public void setMouthClosedPerc(int mouthClosedPerc) {
        MouthClosedPerc = mouthClosedPerc;
        repaint();
    }

}
