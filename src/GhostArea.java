import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class GhostArea {

    public static Area[] getArea( Ghost ghost, boolean field0 ){

        Area[] ghostArea = new Area[3];

        int X;
        int Y;
        int SideLength = ghost.getSideLength();
        boolean LegsTogether = ghost.isLegsTogether();

        if (field0) {
            X = ghost.getX0();
            Y = ghost.getY0();
        } else {
            X = ghost.getX1();
            Y = ghost.getY1();
        }

        if (ghost.isEaten()) {

            ghostArea[0] = null;

        } else {

            //body:
            Area Ghost = new Area( new Ellipse2D.Double(X, Y, SideLength, (double)SideLength / 2) );
            Ghost.add( new Area( new Rectangle(X, Y + SideLength / 4, SideLength, SideLength / 2) ) );

            //inner legs:
            Ghost.add( new Area( new Polygon( new int[]{X + SideLength / 4, X + SideLength / 4, X + SideLength / 2}, new int[]{Y + 3 * SideLength / 4, Y + SideLength, Y + 3 * SideLength / 4}, 3) ) );
            Ghost.add( new Area( new Polygon( new int[]{X + SideLength / 2, X + 3 * SideLength / 4, X + 3 * SideLength / 4 }, new int[]{Y + 3 * SideLength / 4, Y + SideLength, Y + 3 * SideLength / 4}, 3) ) );

            //outer legs:
            Ghost.add( new Area( new Polygon( new int[]{X, X + SideLength / 4, (LegsTogether ? X + SideLength / 4 : X)}, new int[]{Y + 3 * SideLength / 4, Y + 3 * SideLength / 4, Y + SideLength}, 3) ) );
            Ghost.add( new Area( new Polygon( new int[]{X + SideLength, X + 3 * SideLength / 4, (LegsTogether ? X + 3 * SideLength / 4 : X + SideLength) }, new int[]{Y + 3 * SideLength / 4, Y + 3 * SideLength / 4, Y + SideLength}, 3) ) );

            //correcting body:
            int inaccuracy = Y + 3 * SideLength / 4 - ( Y + SideLength / 4 + SideLength / 2 );

            if (inaccuracy != 0){
                Ghost.add (new Area( new Rectangle( X, Y + SideLength / 4 + SideLength / 2, SideLength, inaccuracy )));
            }

            ghostArea[0] = Ghost;

        }

        if (ghost.isScared()){



            //eyes:
            Area eyesAndMouth = new Area( new Rectangle( X + SideLength / 4, Y +  SideLength / 4, SideLength / 8, SideLength / 8 ) );
            eyesAndMouth.add( new Area( new Rectangle(X + 5 * SideLength / 8, Y +  SideLength / 4, SideLength / 8, SideLength / 8) ) );

            //mouth:
            eyesAndMouth.add( new Area( new Polygon(
                    new int[]{
                            X + SideLength / 8,
                            X + SideLength / 4,
                            X + 3 * SideLength / 8,
                            X + SideLength / 2,
                            X + 5 * SideLength / 8,
                            X + 3 * SideLength / 4,
                            X + 7 * SideLength / 8,
                            X + 7 * SideLength / 8,
                            X + 3 * SideLength / 4,
                            X + 5 * SideLength / 8,
                            X + SideLength / 2,
                            X + 3 * SideLength / 8,
                            X + SideLength / 4,
                            X + SideLength / 8
                    },
                    new int[]{
                            Y + SideLength / 2 + SideLength / 8,
                            Y + SideLength / 2,
                            Y + SideLength / 2 + SideLength / 8,
                            Y + SideLength / 2,
                            Y + SideLength / 2 + SideLength / 8,
                            Y + SideLength / 2,
                            Y + SideLength / 2 + SideLength / 8,
                            Y + SideLength / 2 + SideLength / 8 + SideLength / 16,
                            Y + SideLength / 2 + SideLength / 16,
                            Y + SideLength / 2 + SideLength / 8 + SideLength / 16,
                            Y + SideLength / 2 + SideLength / 16,
                            Y + SideLength / 2 + SideLength / 8 + SideLength / 16,
                            Y + SideLength / 2 + SideLength / 16,
                            Y + SideLength / 2 + SideLength / 8 + SideLength / 16,
                    },
                    14
            ) ) );

            ghostArea[1] = eyesAndMouth;
            ghostArea[2] = null;

        } else {

            Directions direction = ghost.getDirection();

            //eyes
            Area eyes = new Area( new Ellipse2D.Double( X + (double)SideLength / 8, Y + (double)SideLength / 4, (double)SideLength / 4, (double)SideLength / 4 ) );
            eyes.add( new Area( new Ellipse2D.Double( X + 5 * (double)SideLength / 8, Y + (double)SideLength / 4, (double)SideLength / 4, (double)SideLength / 4 ) ) );

            //pupils
            Area pupils;

            if ( direction == Directions.Left || direction == Directions.StationaryLeft ) {

                pupils = new Area( new Ellipse2D.Double( X + (double)SideLength / 8, Y + (double)SideLength / 4 + (double)SideLength / 24, (double)SideLength / 6, (double)SideLength / 6 ) );
                pupils.add( new Area( new Ellipse2D.Double(X + (double)5 * SideLength / 8, Y + (double)SideLength / 4 + (double)SideLength / 24, (double)SideLength / 6, (double)SideLength / 6) ) );

            } else if ( direction == Directions.Right || direction == Directions.StationaryRight ) {

                pupils = new Area( new Ellipse2D.Double( X + 3 * (double)SideLength / 8 - (double)SideLength / 6, Y + (double)SideLength / 4 + (double)SideLength / 24, (double)SideLength / 6, (double)SideLength / 6 ));
                pupils.add( new Area( new Ellipse2D.Double( X + 7 * (double)SideLength / 8 - (double)SideLength / 6, Y + (double)SideLength / 4 + (double)SideLength / 24, (double)SideLength / 6, (double)SideLength / 6 )));

            } else if ( direction == Directions.Up || direction == Directions.StationaryUp ) {

                pupils = new Area( new Ellipse2D.Double( X + (double)SideLength / 8 + (double)SideLength / 24, Y + (double)SideLength / 4, (double)SideLength / 6, (double)SideLength / 6 ));
                pupils.add( new Area( new Ellipse2D.Double( X + 5 * (double)SideLength / 8 + (double)SideLength / 24, Y + (double)SideLength / 4, (double)SideLength / 6, (double)SideLength / 6 ) ));

            } else {    //down

                pupils = new Area( new Ellipse2D.Double(X + (double)SideLength / 8 + (double)SideLength / 24, Y + (double)SideLength / 4 + (double)SideLength / 11, (double)SideLength / 6, (double)SideLength / 6  ) );
                pupils.add( new Area( new Ellipse2D.Double( X + 5 * (double)SideLength / 8 + (double)SideLength / 24, Y + (double)SideLength / 4 + (double)SideLength / 11, (double)SideLength / 6, (double)SideLength / 6 ) ) );

            }

            ghostArea[1] = eyes;
            ghostArea[2] = pupils;

        }

        return ghostArea;

    }

}
