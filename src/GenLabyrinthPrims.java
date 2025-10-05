import java.util.LinkedList;

public class GenLabyrinthPrims {

    public static boolean[][] Generate(int m, int n){

        m = m + 2;
        n = n + 2;

        LabirynthCell[][] labyrinth = new LabirynthCell[m][n];

        LinkedList<LabirynthCell> unvisitedNeighbors = new LinkedList<>();

        //generating border:

        for (int i = 0; i < labyrinth.length; i++ ){
            for (int j = 0; j < labyrinth[i].length; j++){

                if (i == 0 || i == labyrinth.length - 1 || j == 0 || j == labyrinth[0].length - 1)
                    labyrinth[i][j] = new LabirynthCell(i, j, true, true);
                else
                    labyrinth[i][j] = new LabirynthCell(i, j, false, true);

            }

        }

        //setting prison: (midpoints horizontally == 'n / 2' (&& if n is even 'n / 2 - 1'))

        //prison:
        labyrinth[m / 2][n / 2].setAll(m / 2, n / 2, true, false);
        labyrinth[m / 2][n / 2 - 1].setAll(m / 2, n / 2 - 1,true, false);
        labyrinth[m / 2][n / 2 + 1].setAll(m / 2, n / 2 + 1,true, false);

        //prison walls:
        labyrinth[m / 2 - 1][n / 2 - 2].setAll(m / 2 - 1, n / 2 - 2,true, true);
        labyrinth[m / 2 - 1][n / 2 - 1].setAll(m / 2 - 1, n / 2 - 1,true, true);
        labyrinth[m / 2 - 1][n / 2].setAll(m / 2 - 1, n / 2,true, true);
        labyrinth[m / 2 - 1][n / 2 + 1].setAll(m / 2 - 1, n / 2 + 1,true, true);
        labyrinth[m / 2 - 1][n / 2 + 2].setAll(m / 2 - 1, n / 2 + 2,true, true);

        labyrinth[m / 2][n / 2 - 2].setAll(m / 2, n / 2 - 2,true, true);
        labyrinth[m / 2][n / 2 + 2].setAll(m / 2, n / 2 + 2,true, true);

        labyrinth[m / 2 + 1][n / 2 - 2].setAll(m / 2 + 1, n / 2 - 2,true, true);
        labyrinth[m / 2 + 1][n / 2 - 1].setAll(m / 2 + 1, n / 2 - 1,true, true);
        labyrinth[m / 2 + 1][n / 2].setAll(m / 2 + 1, n / 2,true, true);
        labyrinth[m / 2 + 1][n / 2 + 1].setAll(m / 2 + 1, n / 2 + 1,true, true);
        labyrinth[m / 2 + 1][n / 2 + 2].setAll(m / 2 + 1, n / 2 + 2,true, true);


        //choosing a starting point for generation:
        labyrinth[ m/2 - 2 ][ n/2 ].setCoordinatesOfCorridor( m/2 - 1, n/2 );
        unvisitedNeighbors.add( labyrinth[ m / 2 - 2][n / 2] );

        while (!unvisitedNeighbors.isEmpty()){

            int UNI = (int)Math.floor(Math.random() * (unvisitedNeighbors.size()));         //unvisitedNeighbors index

            int iX = unvisitedNeighbors.get(UNI).getX();
            int iY = unvisitedNeighbors.get(UNI).getY();

            //setting this cell as visited:
            labyrinth[ iX ][ iY ].becomePath();

            //making a corridor:
            labyrinth[ unvisitedNeighbors.get(UNI).getxOfCorridor() ][ unvisitedNeighbors.get(UNI).getyOfCorridor() ].becomePath();


            //looking for new unvisited neighbours:

            //to the left:
            if ( iY - 2 >= 0 && !labyrinth[ iX ][ iY - 2 ].wasVisited() ){

                //unvisited neighbor found on the left
                labyrinth[ iX ][ iY - 2 ].setCoordinatesOfCorridor( iX, iY - 1 );
                unvisitedNeighbors.add( labyrinth[ iX ][ iY - 2 ] );


            }

            //to the right:
            if ( iY + 2 < labyrinth[0].length && !labyrinth[ iX ][ iY + 2 ].wasVisited() ){

                //unvisited neighbor found on the right
                labyrinth[ iX ][ iY + 2 ].setCoordinatesOfCorridor( iX, iY + 1 );
                unvisitedNeighbors.add( labyrinth[ iX ][ iY + 2 ] );

            }

            //up:
            if ( iX - 2 >= 0 && !labyrinth[ iX - 2 ][ iY ].wasVisited() ){

                //unvisited neighbor found above
                labyrinth[ iX - 2 ][ iY ].setCoordinatesOfCorridor( iX - 1 , iY );
                unvisitedNeighbors.add( labyrinth[ iX - 2 ][ iY ] );

            }

            //down:
            if ( iX + 2 < labyrinth[0].length && !labyrinth[ iX + 2 ][ iY ].wasVisited() ){

                //unvisited neighbor found below
                labyrinth[ iX + 2 ][ iY ].setCoordinatesOfCorridor( iX + 1 , iY );
                unvisitedNeighbors.add( labyrinth[ iX + 2 ][ iY ] );

            }

            //remove this cell form the list of unvisited cells:
            unvisitedNeighbors.remove( UNI );

        }

        //deleting dead ends:
        for (int i = 0; i < labyrinth.length; i++){
            for (int j = 0; j < labyrinth[i].length; j++){

                if ( !labyrinth[i][j].isWall() && !( i == m / 2 && j == n / 2 || i == m / 2 && j == n / 2 - 1 || i == m / 2 && j == n / 2 + 1) ){

                    int emptycount = 0;

                    if (labyrinth[i - 1][j].isWall())
                        emptycount++;

                    if (labyrinth[i + 1][j].isWall())
                        emptycount++;

                    if (labyrinth[i][j + 1].isWall())
                        emptycount++;

                    if (labyrinth[i][j - 1].isWall())
                        emptycount++;

                    if (emptycount >= 3){       //if this field is a dead end

                        if (!labyrinth[i - 1][j].wasVisited()){

                            labyrinth[i - 1][j].becomePath();

                        } else {

                            if (!labyrinth[i + 1][j].wasVisited()){

                                labyrinth[i + 1][j].becomePath();

                            } else {

                                if (!labyrinth[i][j + 1].wasVisited()){

                                    labyrinth[i][j + 1].becomePath();

                                } else {

                                    labyrinth[i][j - 1].becomePath();

                                }

                            }

                        }

                    }

                }

            }
        }

        //fixing double edges:

        boolean deoT = isDoubleLine(m/2, 0, false);                             //double edge on Top
        boolean deoL = isDoubleLine(n/2, 0, false);                             // ... Left
        boolean deoR = isDoubleLine(n/2, labyrinth[0].length - 1, true);        // ... Right
        boolean deoB = isDoubleLine(m/2, labyrinth.length - 1, true);           // ... Bottom

        if (deoT || deoB)
            for(int i = 1; i < labyrinth[0].length - 1; i++){

                if (deoT)
                    if (!labyrinth[2][i].isWall() && !labyrinth[2][i - 1].isWall() && !labyrinth[2][i + 1].isWall())
                        labyrinth[1][i].becomeWall();
                    else
                        labyrinth[1][i].becomePath();

                if (deoB)
                    if (!labyrinth[labyrinth.length - 3][i].isWall() && !labyrinth[labyrinth.length - 3][i - 1].isWall() && !labyrinth[labyrinth.length - 3][i + 1].isWall())
                        labyrinth[labyrinth.length - 2][i].becomeWall();
                    else
                        labyrinth[labyrinth.length - 2][i].becomePath();

            }

        if (deoL || deoR)
            for(int i = 1; i < labyrinth.length - 1; i++){

                if (deoL)
                    if (!labyrinth[i - 1][2].isWall() && !labyrinth[i][2].isWall() && !labyrinth[i + 1][2].isWall())
                        labyrinth[i][1].becomeWall();
                    else
                        labyrinth[i][1].becomePath();

                if (deoR)
                    if (!labyrinth[i - 1][labyrinth[0].length - 3].isWall() && !labyrinth[i][labyrinth[0].length - 3].isWall() && !labyrinth[i + 1][labyrinth[0].length - 3].isWall())
                        labyrinth[i][labyrinth[0].length - 2].becomeWall();
                    else
                        labyrinth[i][labyrinth[0].length - 2].becomePath();

            }

        //fixing corners:

        //top left:
        if ( ( deoT || deoL ) && !labyrinth[2][2].isWall() )
            labyrinth[1][1].becomeWall();

        //top right:
        if ( ( deoT || deoR ) && !labyrinth[2][labyrinth[0].length - 3].isWall() )
            labyrinth[1][labyrinth[0].length - 2].becomeWall();

        //bottom left:
        if ( ( deoB || deoL ) && !labyrinth[labyrinth.length - 3][2].isWall() )
            labyrinth[labyrinth.length - 2][1].becomeWall();

        //bottom right:
        if ( ( deoB || deoR ) && !labyrinth[labyrinth.length - 3][labyrinth[0].length - 3].isWall() )
            labyrinth[labyrinth.length - 2][labyrinth[0].length - 2].becomeWall();

        //iterating one last time to clean up all the unwanted artifacts:
        for (int i = 0; i < labyrinth.length; i++){
            for (int j = 0; j < labyrinth[i].length; j++){

                if ( !labyrinth[i][j].isWall() && !( i == m / 2 && j == n / 2 || i == m / 2 && j == n / 2 - 1 || i == m / 2 && j == n / 2 + 1) ){

                    int emptycount = 0;

                    if (labyrinth[i - 1][j].isWall())
                        emptycount++;

                    if (labyrinth[i + 1][j].isWall())
                        emptycount++;

                    if (labyrinth[i][j + 1].isWall())
                        emptycount++;

                    if (labyrinth[i][j - 1].isWall())
                        emptycount++;

                    if (emptycount >= 3){       //if this field is a dead end

                        labyrinth[i][j].becomeWall();

                    }

                }

            }
        }

        //rewriting the generated labyrinth to a simple boolean array:
        boolean[][] ret = new boolean[m][n];

        for (int i = 0; i < labyrinth.length; i++){
            for (int j = 0; j < labyrinth[i].length; j++){

                ret[i][j] = labyrinth[i][j].isWall();

            }
        }

        return ret;

    }

    private static boolean isDoubleLine (int startIndex, int finishIndex, boolean ascending){

        boolean doubleLine = false;

        if (ascending)
            while (startIndex < finishIndex)
                startIndex += 2;

        else
            while (startIndex > finishIndex)
                startIndex -= 2;

        if (startIndex == finishIndex)
            doubleLine = true;

        return doubleLine;

    }

}
