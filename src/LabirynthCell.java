public class LabirynthCell {

    private int x;
    private int y;
    private int xOfCorridor;
    private int yOfCorridor;

    private boolean visited;
    private boolean wall;


    public LabirynthCell(int x, int y, boolean visited, boolean wall){

        this.x = x;
        this.y = y;
        this.visited = visited;
        this.wall = wall;
        xOfCorridor= 0;
        yOfCorridor = 0;

    }

    public void setAll(int x, int y, boolean visited, boolean wall){

        this.x = x;
        this.y = y;
        this.visited = visited;
        this.wall = wall;

    }

    public void setCoordinatesOfCorridor (int xOfCorridor, int yOfCorridor){

        this.xOfCorridor = xOfCorridor;
        this.yOfCorridor = yOfCorridor;

    }

    public void becomePath(){
        this.visited = true;
        this.wall = false;
    }

    public void becomeWall(){
        this.wall = true;
    }

    public boolean wasVisited() {
        return visited;
    }

    public boolean isWall() {
        return wall;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getxOfCorridor() {
        return xOfCorridor;
    }

    public int getyOfCorridor() {
        return yOfCorridor;
    }
}
