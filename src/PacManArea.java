import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class PacManArea {

    public static Area getArea(PacMan pm, boolean field0){

        int X;
        int Y;
        int SideLength = pm.getSideLength();
        int MouthClosedPerc = pm.getMouthClosedPerc();
        int EyeLength = SideLength / 5;
        Directions direction = pm.getDirections();

        if (field0){
            X = pm.getX0();
            Y = pm.getY0();
        } else {
            X = pm.getX1();
            Y = pm.getY1();
        }

        Area PacMan = new Area( new Ellipse2D.Double(X, Y, SideLength, SideLength) );
        Area mouth;
        Area eye;

        int mouthBottomX, mouthBottomY, mouthTopX, mouthTopY, eyeX, eyeY;

        if ( direction == Directions.Left || direction == Directions.StationaryLeft ){

            mouthTopX = X;
            mouthTopY = Y + SideLength / 2 * MouthClosedPerc / 100;

            mouthBottomX = X;
            mouthBottomY = Y + SideLength - SideLength / 2 * MouthClosedPerc / 100;

            eyeX = X + SideLength / 2 - EyeLength / 2;
            eyeY = Y + SideLength / 7;

        } else if ( direction == Directions.Right || direction == Directions.StationaryRight ){

            mouthTopX = X + SideLength;
            mouthTopY = Y + SideLength / 2 * MouthClosedPerc / 100;

            mouthBottomX = X + SideLength;
            mouthBottomY = Y + SideLength - SideLength / 2 * MouthClosedPerc / 100;

            eyeX = X + SideLength / 2 - EyeLength / 2;
            eyeY = Y + SideLength / 7;

        } else if ( direction == Directions.Up || direction == Directions.StationaryUp ){

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

        return PacMan;

    }

}
