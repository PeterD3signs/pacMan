import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWindow extends JFrame implements CloseOpenedThreadsAfterCtrlShiftQ, UpdateCells {

    private int score;
    private int cellSize;
    private int highScore;
    private boolean justOpened = true;
    private int noOfPointsLeft;
    private PacMan pm;
    private Ghost Blinky;
    private Ghost Pinky;
    private Ghost Inky;
    private Ghost Clyde;
    private final Board board;
    private MovePmThread mpt;
    private PointContactThread pct;
    private GhostContactThread gct;
    private PowerUpFlashThread puft;
    private PowerUpContactThread puct;
    private ApplyPowerUpThread aput;
    private MoveGhostThread moveBlinky;
    private MoveGhostThread movePinky;
    private MoveGhostThread moveInky;
    private MoveGhostThread moveClyde;
    private final CustomImageLabel s0;
    private final CustomImageLabel s1;
    private final CustomImageLabel s2;
    private final CustomImageLabel s3;
    private final CustomImageLabel s4;
    private final CustomImageLabel s5;
    private final CustomImageLabel hs0;
    private final CustomImageLabel hs1;
    private final CustomImageLabel hs2;
    private final CustomImageLabel hs3;
    private final CustomImageLabel hs4;
    private final CustomImageLabel hs5;
    private final CustomImageLabel life0;
    private final CustomImageLabel life1;
    private final CustomImageLabel life2;
    private final CustomImageLabel powerUpIconLabel;
    private int eatGhostReward = 150;

    public GameWindow(int gameWidth, int gameHeight){

        //Setting up:
        if (justOpened){

            cellSize = 10;
            noOfPointsLeft = 0;

            pm = new PacMan(Directions.StationaryLeft, Color.YELLOW, 50, (int)((float)cellSize / 1.1), this);

            Blinky = new Ghost(GhostNames.Blinky, Color.RED, Color.RED, (int)((float)cellSize / 1.1), false, Directions.StationaryRight, false, false, this);
            Pinky = new Ghost(GhostNames.Pinky, Color.PINK, Color.PINK, (int)((float)cellSize / 1.1), false, Directions.StationaryLeft, false, false, this);
            Inky = new Ghost(GhostNames.Inky, Color.CYAN, Color.CYAN, (int)((float)cellSize / 1.1), false, Directions.StationaryUp, false, false, this);
            Clyde = new Ghost(GhostNames.Clyde, Color.ORANGE, Color.ORANGE, (int)((float)cellSize / 1.1), false, Directions.StationaryUp, false, false, this);

            aput = new ApplyPowerUpThread(this);
            puct = new PowerUpContactThread(pm, this);
            puft = new PowerUpFlashThread(this);
            gct = new GhostContactThread(pm, Blinky, Pinky, Inky, Clyde, this);
            pct = new PointContactThread(pm, this);
            mpt = new MovePmThread(pm, this, cellSize);
            moveBlinky = new MoveGhostThread(Blinky, this, cellSize, Directions.Right);
            movePinky = new MoveGhostThread(Pinky, this, cellSize, Directions.Left);
            moveInky = new MoveGhostThread(Inky, this, cellSize, Directions.Up);
            moveClyde = new MoveGhostThread(Clyde, this, cellSize, Directions.Up);

            try{
                highScore = HighScoresReadAndWrite.getHighestScore();
            } catch (Exception ex){
                ExceptionLog.addError(ex.getMessage());
            }

            score = 0;
            justOpened = false;

        }

        //Adding Panels:
        JPanel wholeWindowPanel = new JPanel( new BorderLayout() );

        JPanel topInfoPanel = new JPanel( new GridLayout(2, 3, 0, 0) );
        JPanel bottomInfoPanel = new JPanel( new GridLayout(1, 3, 0, 0) );

        JPanel scorePanel = new JPanel( new FlowLayout( FlowLayout.LEADING ) );
        JPanel highScorePanel = new JPanel( new FlowLayout( FlowLayout.TRAILING ) );
        JPanel highScoreLP = new JPanel( new FlowLayout( FlowLayout.TRAILING ) );       //panel for the high score label
        JPanel scoreLP = new JPanel( new FlowLayout() );
        JPanel livesPanel = new JPanel( new FlowLayout( FlowLayout.LEADING ) );
        JPanel powerUpPanel = new JPanel( new FlowLayout( FlowLayout.TRAILING ) );

        JPanel boardPanel = new JPanel( new FlowLayout() );


        //Adding the game JTable:
        board = new Board(gameWidth, gameHeight, cellSize, pm, Blinky, Pinky, Inky, Clyde, this);


        //Adding Labels:
        CustomImageLabel scoreLabel = new CustomImageLabel("resources/ScoreText.png", "Score:", 30, 190, 30, 114, true);

        s0 = new CustomImageLabel("resources/number0.png", "0", 30, 110, 30, 10, true);
        s1 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        s2 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        s3 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        s4 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        s5 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);

        CustomImageLabel highScoreLabel = new CustomImageLabel("resources/HighScoreSingularText.png", "High score:", 30, 190, 30, 114, true);

        hs0 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        hs1 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        hs2 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        hs3 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        hs4 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 10, true);
        hs5 = new CustomImageLabel("resources/number0.png", "0", 30, 110, 30, 10, true);

        CustomImageLabel powerUpTextLabel = new CustomImageLabel("resources/PowerUpText.png", "Power up:", 30, 190, 30, 10, true);
        powerUpIconLabel = new CustomImageLabel("resources/NullPowerUp.png", "   ", 30, 190, 30, 10, true);

        life0 = new CustomImageLabel("resources/heartIcon.png", "<3", 30, 190, 30, 10, true);
        life1 = new CustomImageLabel("resources/heartIcon.png", "<3", 30, 190, 30, 10, true);
        life2 = new CustomImageLabel("resources/heartIcon.png", "<3", 30, 190, 30, 10, true);

        Label pl0 = new Label();        //placeholder labels
        Label pl1 = new Label();
        Label pl2 = new Label();


        //Scaling:
        highScorePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                if (!justOpened){

                    hs0.SetNewSize(((double)highScorePanel.getWidth() / 7 - 1));
                    hs1.SetNewSize(((double)highScorePanel.getWidth() / 7 - 1));
                    hs2.SetNewSize(((double)highScorePanel.getWidth() / 7 - 1));
                    hs3.SetNewSize(((double)highScorePanel.getWidth() / 7 - 1));
                    hs4.SetNewSize(((double)highScorePanel.getWidth() / 7 - 1));
                    hs5.SetNewSize(((double)highScorePanel.getWidth() / 7 - 1));

                    s0.SetNewSize(((double)scorePanel.getWidth() / 7 - 1));
                    s1.SetNewSize(((double)scorePanel.getWidth() / 7 - 1));
                    s2.SetNewSize(((double)scorePanel.getWidth() / 7 - 1));
                    s3.SetNewSize(((double)scorePanel.getWidth() / 7 - 1));
                    s4.SetNewSize(((double)scorePanel.getWidth() / 7 - 1));
                    s5.SetNewSize(((double)scorePanel.getWidth() / 7 - 1));

                    highScoreLabel.SetNewSize((highScoreLP.getWidth()));

                    scoreLabel.SetNewSize((scoreLP.getWidth()));

                    life0.SetNewSize(((double) livesPanel.getWidth() / 4 - 1));
                    life1.SetNewSize(((double) livesPanel.getWidth() / 4 - 1));
                    life2.SetNewSize(((double) livesPanel.getWidth() / 4 - 1));

                    powerUpTextLabel.SetNewSize(((double) powerUpPanel.getWidth() * 3 / 4 - 1));
                    powerUpIconLabel.SetNewSize(((double) powerUpPanel.getWidth() / 6 - 1));

                    cellSize = board.changeSize(boardPanel.getWidth(), boardPanel.getHeight(), gameWidth, gameHeight, pm, Blinky, Pinky, Inky, Clyde);

                    mpt.setStepLength(cellSize);
                    moveBlinky.setStepLength(cellSize);
                    movePinky.setStepLength(cellSize);
                    moveInky.setStepLength(cellSize);
                    moveClyde.setStepLength(cellSize);

                }

            }
        });


        //Setting layout:
        scorePanel.add(s0);
        scorePanel.add(s1);
        scorePanel.add(s2);
        scorePanel.add(s3);
        scorePanel.add(s4);
        scorePanel.add(s5);

        highScorePanel.add(hs0);
        highScorePanel.add(hs1);
        highScorePanel.add(hs2);
        highScorePanel.add(hs3);
        highScorePanel.add(hs4);
        highScorePanel.add(hs5);

        highScoreLP.add(highScoreLabel);
        scoreLP.add(scoreLabel);

        topInfoPanel.add(scoreLP);
        topInfoPanel.add(pl0);
        topInfoPanel.add(highScoreLP);
        topInfoPanel.add(scorePanel);
        topInfoPanel.add(pl1);
        topInfoPanel.add(highScorePanel);


        livesPanel.add(life2);
        livesPanel.add(life1);
        livesPanel.add(life0);

        powerUpPanel.add(powerUpTextLabel);
        powerUpPanel.add(powerUpIconLabel);

        bottomInfoPanel.add(livesPanel);
        bottomInfoPanel.add(pl2);
        bottomInfoPanel.add(powerUpPanel);

        boardPanel.add(board);

        wholeWindowPanel.add( topInfoPanel, BorderLayout.PAGE_START );
        wholeWindowPanel.add( boardPanel , BorderLayout.CENTER );
        wholeWindowPanel.add( bottomInfoPanel, BorderLayout.PAGE_END );


        //Adding CtrlShiftQListener:
        GameWindow hostFrame = this;
        wholeWindowPanel.setFocusable(true);
        wholeWindowPanel.requestFocus();
        wholeWindowPanel.addKeyListener( new CtrlShiftQListener(hostFrame) );
        wholeWindowPanel.addKeyListener( new MovementKeyListener(hostFrame));


        //Adding the panel to the frame:
        add(wholeWindowPanel);


        //JFrame settings:
        pack();
        setExtendedState( JFrame.MAXIMIZED_BOTH );
        setMinimumSize( new Dimension(300, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage( ProgrammeIcon.getIcon() );
        setTitle("PAC-MAN");
        setVisible(true);

        setHighScore();

        //updating the JTable one last time before starting:
        board.changeSize(boardPanel.getWidth(), boardPanel.getHeight(), gameWidth, gameHeight, pm, Blinky, Pinky, Inky, Clyde );

        //passing ghosts and PacMan to the cells:
        board.updateCreatures(pm, Blinky, Pinky, Inky, Clyde);

        aput.start();
        puct.start();
        puft.start();
        gct.start();
        mpt.start();
        pct.start();
        moveBlinky.start();
        movePinky.start();
        moveInky.start();
        moveClyde.start();
        Blinky.startThread();
        Pinky.startThread();
        Inky.startThread();
        Clyde.startThread();

    }

    @Override
    public void closeThreads() {

        aput.stopRunning();
        puct.stopRunning();
        puft.stopRunning();
        gct.stopRunning();
        pct.stopRunning();
        mpt.stopRunning();
        moveBlinky.stopRunning();
        movePinky.stopRunning();
        moveInky.stopRunning();
        moveClyde.stopRunning();

        pm.stopThread();
        Blinky.stopThread();
        Pinky.stopThread();
        Inky.stopThread();
        Clyde.stopThread();

    }

    @Override
    public void updateCells(int m0, int n0, int m1, int n1) {

        if (board != null)
            board.updateCells(m0, n0, m1, n1);

    }

    public void notifyCellOfPmPresence(int m, int n, boolean present){

        if (board != null)
            ((BoardModel)(board.getModel())).notifyCellOfPmPresence(m, n, present);

    }

    public void notifyCellOfGhostPresence(GhostNames ghostName, int m, int n, boolean present){

        if (board != null)
            ((BoardModel)(board.getModel())).notifyCellOfGhostPresence(ghostName, m, n, present);

    }

    public void updateKeyPressed(Directions direction){
        mpt.keyPressed(direction);
    }

    public CustomField getCellAt(int m, int n){
        return (CustomField) board.getModel().getValueAt(m, n);
    }

    public int getCellSizeFromModel(){
        return ((BoardModel)(board.getModel())).getCellSideSize();
    }

    public CustomField[][] getCells(){
        return ((BoardModel)(board.getModel())).getCells();
    }

    public void setHighScore(){

        int score = this.highScore;

        if (score < 10){

            hs5.ChangeImage(("resources/number" + score + ".png" ), score + "");

        } else if (score < 100){

            int digit0 = score / 10;
            int digit1 = score - digit0 * 10;

            hs4.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
            hs5.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");

        } else if (score < 1000){

            int digit0 = score / 100;
            int digit1 = ( score - digit0 * 100 ) / 10;
            int digit2 = score - digit0 * 100 - digit1 * 10;

            hs3.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
            hs4.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
            hs5.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");

        } else if (score < 10000){

            int digit0 = score / 1000;
            int digit1 = ( score - digit0 * 1000 ) / 100;
            int digit2 = ( score - digit0 * 1000 - digit1 * 100 ) / 10;
            int digit3 = score - digit0 * 1000 - digit1 * 100 - digit2 * 10;

            hs2.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
            hs3.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
            hs4.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");
            hs5.ChangeImage(("resources/number" + digit3 + ".png" ), digit3 + "");

        } else if (score < 100000){

            int digit0 = score / 10000;
            int digit1 = ( score - digit0 * 10000 ) / 1000;
            int digit2 = ( score - digit0 * 10000 - digit1 * 1000 ) / 100;
            int digit3 = ( score - digit0 * 10000 - digit1 * 1000 - digit2 * 100 ) / 10;
            int digit4 = score - digit0 * 10000 - digit1 * 1000 - digit2 * 100 - digit3 * 10;

            hs1.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
            hs2.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
            hs3.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");
            hs4.ChangeImage(("resources/number" + digit3 + ".png" ), digit3 + "");
            hs5.ChangeImage(("resources/number" + digit4 + ".png" ), digit4 + "");

        } else {

            int digit0 = score / 100000;
            int digit1 = ( score - digit0 * 100000 ) / 10000;
            int digit2 = ( score - digit0 * 100000 - digit1 * 10000 ) / 1000;
            int digit3 = ( score - digit0 * 100000 - digit1 * 10000 - digit2 * 1000 ) / 100;
            int digit4 = ( score - digit0 * 100000 - digit1 * 10000 - digit2 * 1000 - digit3 * 100 ) / 10;
            int digit5 = score - digit0 * 100000 - digit1 * 10000 - digit2 * 1000 - digit3 * 100 - digit4 * 10;

            hs0.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
            hs1.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
            hs2.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");
            hs3.ChangeImage(("resources/number" + digit3 + ".png" ), digit3 + "");
            hs4.ChangeImage(("resources/number" + digit4 + ".png" ), digit4 + "");
            hs5.ChangeImage(("resources/number" + digit5 + ".png" ), digit5 + "");

        }

    }

    public void increaseScore(int pointIncrease){

        score = score + pointIncrease;

        int score = this.score;

        if (score > 999999){

            s0.ChangeImage("resources/above.png" , ">");
            s1.ChangeImage("resources/numberNull.png" , " ");
            s2.ChangeImage("resources/number1.png" , "1");
            s3.ChangeImage("resources/m.png" , "m");
            s4.ChangeImage("resources/i.png" , "i");
            s5.ChangeImage("resources/l.png" , "l");

            hs0.ChangeImage("resources/above.png" , ">");
            hs1.ChangeImage("resources/numberNull.png" , " ");
            hs2.ChangeImage("resources/number1.png" , "1");
            hs3.ChangeImage("resources/m.png" , "m");
            hs4.ChangeImage("resources/i.png" , "i");
            hs5.ChangeImage("resources/l.png" , "l");

        } else {

            int scoreLength;

            if (score < 10){

                s0.ChangeImage(("resources/number" + score + ".png" ), score + "");

                scoreLength = 1;

            } else if (score < 100){

                int digit0 = score / 10;
                int digit1 = score - digit0 * 10;

                s0.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
                s1.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");

                scoreLength = 2;

            } else if (score < 1000){

                int digit0 = score / 100;
                int digit1 = ( score - digit0 * 100 ) / 10;
                int digit2 = score - digit0 * 100 - digit1 * 10;

                s0.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
                s1.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
                s2.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");

                scoreLength = 3;

            } else if (score < 10000){

                int digit0 = score / 1000;
                int digit1 = ( score - digit0 * 1000 ) / 100;
                int digit2 = ( score - digit0 * 1000 - digit1 * 100 ) / 10;
                int digit3 = score - digit0 * 1000 - digit1 * 100 - digit2 * 10;

                s0.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
                s1.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
                s2.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");
                s3.ChangeImage(("resources/number" + digit3 + ".png" ), digit3 + "");

                scoreLength = 4;

            } else if (score < 100000){

                int digit0 = score / 10000;
                int digit1 = ( score - digit0 * 10000 ) / 1000;
                int digit2 = ( score - digit0 * 10000 - digit1 * 1000 ) / 100;
                int digit3 = ( score - digit0 * 10000 - digit1 * 1000 - digit2 * 100 ) / 10;
                int digit4 = score - digit0 * 10000 - digit1 * 1000 - digit2 * 100 - digit3 * 10;

                s0.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
                s1.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
                s2.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");
                s3.ChangeImage(("resources/number" + digit3 + ".png" ), digit3 + "");
                s4.ChangeImage(("resources/number" + digit4 + ".png" ), digit4 + "");

                scoreLength = 5;

            } else {

                int digit0 = score / 100000;
                int digit1 = ( score - digit0 * 100000 ) / 10000;
                int digit2 = ( score - digit0 * 100000 - digit1 * 10000 ) / 1000;
                int digit3 = ( score - digit0 * 100000 - digit1 * 10000 - digit2 * 1000 ) / 100;
                int digit4 = ( score - digit0 * 100000 - digit1 * 10000 - digit2 * 1000 - digit3 * 100 ) / 10;
                int digit5 = score - digit0 * 100000 - digit1 * 10000 - digit2 * 1000 - digit3 * 100 - digit4 * 10;

                s0.ChangeImage(("resources/number" + digit0 + ".png" ), digit0 + "");
                s1.ChangeImage(("resources/number" + digit1 + ".png" ), digit1 + "");
                s2.ChangeImage(("resources/number" + digit2 + ".png" ), digit2 + "");
                s3.ChangeImage(("resources/number" + digit3 + ".png" ), digit3 + "");
                s4.ChangeImage(("resources/number" + digit4 + ".png" ), digit4 + "");
                s5.ChangeImage(("resources/number" + digit5 + ".png" ), digit5 + "");

                scoreLength = 6;

            }

            //updating highScore:
            if (score > highScore){

                highScore = score;

                if (scoreLength == 1){

                    hs5.ChangeImage(s0.getImagePath(),s0.getImageBackupText() );

                } else if (scoreLength == 2){

                    hs5.ChangeImage(s1.getImagePath(),s1.getImageBackupText() );
                    hs4.ChangeImage(s0.getImagePath(),s0.getImageBackupText() );

                } else if (scoreLength == 3){

                    hs5.ChangeImage(s2.getImagePath(),s2.getImageBackupText() );
                    hs4.ChangeImage(s1.getImagePath(),s1.getImageBackupText() );
                    hs3.ChangeImage(s0.getImagePath(),s0.getImageBackupText() );

                } else if (scoreLength == 4){

                    hs5.ChangeImage(s3.getImagePath(),s3.getImageBackupText() );
                    hs4.ChangeImage(s2.getImagePath(),s2.getImageBackupText() );
                    hs3.ChangeImage(s1.getImagePath(),s1.getImageBackupText() );
                    hs2.ChangeImage(s0.getImagePath(),s0.getImageBackupText() );

                } else if (scoreLength == 5){

                    hs5.ChangeImage(s4.getImagePath(),s4.getImageBackupText() );
                    hs4.ChangeImage(s3.getImagePath(),s3.getImageBackupText() );
                    hs3.ChangeImage(s2.getImagePath(),s2.getImageBackupText() );
                    hs2.ChangeImage(s1.getImagePath(),s1.getImageBackupText() );
                    hs1.ChangeImage(s0.getImagePath(),s0.getImageBackupText() );

                } else {

                    hs5.ChangeImage(s5.getImagePath(),s5.getImageBackupText() );
                    hs4.ChangeImage(s4.getImagePath(),s4.getImageBackupText() );
                    hs3.ChangeImage(s3.getImagePath(),s3.getImageBackupText() );
                    hs2.ChangeImage(s2.getImagePath(),s2.getImageBackupText() );
                    hs1.ChangeImage(s1.getImagePath(),s1.getImageBackupText() );
                    hs0.ChangeImage(s0.getImagePath(),s0.getImageBackupText() );

                }

            }

        }

    }

    public void setCellHasPoint(int m, int n, boolean hasPoint){
        ((BoardModel)(board.getModel())).setCellHasPoint(m, n, hasPoint);
    }

    public void createPowerUp(int m, int n){
        ((BoardModel)(board.getModel())).createPowerUp(m, n);
    }

    public void deletePowerUp(int m, int n){
        ((BoardModel)(board.getModel())).deletePowerUp(m, n);
    }

    public void subtractOnePoint(){
        noOfPointsLeft = noOfPointsLeft - 1;
    }

    public void setPowerUpsVisibility(boolean visible){
        ((BoardModel)(board.getModel())).setPowerUpsVisibility(visible);
    }

    public void setNoOfPointsLeft(int noOfPointsLeft){
        this.noOfPointsLeft = noOfPointsLeft;
    }

    public int getNoOfPointsLeft(){
        return noOfPointsLeft;
    }

    public void subtractPoint(){
        noOfPointsLeft = noOfPointsLeft - 1;
    }

    public void replenishPoints(){
        ((BoardModel)(board.getModel())).replenishPoints();
    }

    public void death(){

        if (life0.getImagePath().equals("resources/heartIcon.png")){

            life0.ChangeImage("resources/nullHeartIcon.png", "");
            aput.applyInvulnerability();

        } else {

            if (life1.getImagePath().equals("resources/heartIcon.png")){

                life1.ChangeImage("resources/nullHeartIcon.png", "");
                aput.applyInvulnerability();

            } else {

                life2.ChangeImage("resources/nullHeartIcon.png", "");

                gameEnd();

            }

        }

    }

    public void applyPowerUp(){

        aput.applyNewPowerUp();

    }

    public void setThreeTimesPoints(boolean set){

        if (set){
            pct.setIncreaseAmount(3);
            puct.setIncreaseAmount(150);
        } else {
            pct.setIncreaseAmount(1);
            puct.setIncreaseAmount(50);
        }

    }

    public void setPmInvulnerable(boolean set){

        pm.setImmortal(set);

    }

    public void setTripleSpeed(boolean set){

        mpt.setSpeedBonus(set);

    }

    public void freezeGhosts(boolean set){

        moveBlinky.freeze(set);
        movePinky.freeze(set);
        moveInky.freeze(set);
        moveClyde.freeze(set);

        Blinky.setPause(set);
        Pinky.setPause(set);
        Inky.setPause(set);
        Clyde.setPause(set);

    }

    public void setGhostScared(boolean set){

        Blinky.setScared(set);
        Pinky.setScared(set);
        Inky.setScared(set);
        Clyde.setScared(set);

        if (!set)
            resetEatGhostReward();

    }

    public void setPowerUpGraphic(int newPowerUp){

        //0 - null, 1 - eatGhosts, 2 - freezeGhosts, 3 - triple the speed, 4 - invulnerability, 5 - 3x points

        if (newPowerUp == 1) {

            powerUpIconLabel.ChangeImage("resources/eatGhostsPowerUp.png", "eat");

        } else if (newPowerUp == 2){

            powerUpIconLabel.ChangeImage("resources/ghostsStopPowerUp.png", "|| ");

        } else if (newPowerUp == 3){

            powerUpIconLabel.ChangeImage("resources/speedPowerUp.png", ">>>");

        } else if (newPowerUp == 4){

            powerUpIconLabel.ChangeImage("resources/shieldPowerUp.png", "def");

        } else if (newPowerUp == 5){

            powerUpIconLabel.ChangeImage("resources/3xPowerUp.png", "3x ");

        } else {

            powerUpIconLabel.ChangeImage("resources/NullPowerUp.png", "   ");

        }

    }

    public void incrementEatGhostReward(){
        eatGhostReward = eatGhostReward + 150;
    }

    public void resetEatGhostReward(){
        eatGhostReward = 150;
    }

    public int getEatGhostReward(){
        return eatGhostReward;
    }

    public void gameEnd(){

        closeThreads();

        Main.LaunchEndScreen(score, s0.getImageBackupText(), s1.getImageBackupText(), s2.getImageBackupText(), s3.getImageBackupText(), s4.getImageBackupText(), s5.getImageBackupText(),
                s0.getImagePath(), s1.getImagePath(), s2.getImagePath(), s3.getImagePath(), s4.getImagePath(), s5.getImagePath());

        this.dispose();

    }



}
