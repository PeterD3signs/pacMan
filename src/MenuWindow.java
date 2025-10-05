import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuWindow extends JFrame implements MouthClosedPercPassing {

    private final PacManAnimationPanel pmap;
    private final ChangeMouthClosedPercThread cmcpt = new ChangeMouthClosedPercThread(0, true, this);
    private final ShiftLeftThread slt = new ShiftLeftThread(this);


    public MenuWindow() {

        int buttonX = 190;       //original graphics for menu buttons are in scale 19 x 3
        int buttonY = 30;
        int minButtonX = 114;
        int minButtonY = 18;

        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        JPanel buttonSpace = new JPanel();
        buttonSpace.setLayout(new FlowLayout());

        pmap = new PacManAnimationPanel(610, 300);

        CustomButton NewGame = new CustomButton("resources/newGameButton.png", "resources/newGameRollOverButton.png", "New Game", buttonX, buttonY, minButtonX, minButtonY);
        CustomButton HighScores = new CustomButton("resources/highScoreButton.png", "resources/highScoreRollOverButton.png", "High Scores", buttonX, buttonY, minButtonX, minButtonY);
        CustomButton Exit = new CustomButton("resources/exitButton.png", "resources/exitRollOverButton.png", "Exit", buttonX, buttonY, minButtonX, minButtonY);


        NewGame.addActionListener(e -> {

            cmcpt.stopRunning();
            slt.stopRunning();

            Main.LaunchGameSettings();

            Container tempFrame = NewGame.getParent();

            while (!(tempFrame instanceof JFrame))
                tempFrame = tempFrame.getParent();

            ((JFrame) tempFrame).dispose();

        });

        Exit.addActionListener(e -> {

            cmcpt.stopRunning();
            slt.stopRunning();

            Container tempFrame = Exit.getParent();

            while (!(tempFrame instanceof JFrame))
                tempFrame = tempFrame.getParent();

            ((JFrame) tempFrame).dispose();

        });

        HighScores.addActionListener(e -> {

            cmcpt.stopRunning();
            slt.stopRunning();

            Main.LaunchHighScores();

            Container tempFrame = HighScores.getParent();

            while (!(tempFrame instanceof JFrame))
                tempFrame = tempFrame.getParent();

            ((JFrame) tempFrame).dispose();

        });

        NewGame.addKeyListener( new CtrlShiftEListener(this) );
        Exit.addKeyListener( new CtrlShiftEListener(this) );
        HighScores.addKeyListener( new CtrlShiftEListener(this) );


        buttonSpace.add(NewGame);
        buttonSpace.add(HighScores);
        buttonSpace.add(Exit);

        menu.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                NewGame.SetNewSize(((double) menu.getWidth() - 40) / 3);
                HighScores.SetNewSize(((double) menu.getWidth() - 40) / 3);
                Exit.SetNewSize(((double) menu.getWidth() - 40) / 3);

            }
        });

        menu.add(pmap, BorderLayout.CENTER);
        menu.add(buttonSpace, BorderLayout.PAGE_END);

        JScrollPane scrollMenu = new JScrollPane(menu);

        setIconImage( ProgrammeIcon.getIcon() );
        setTitle("PAC-MAN");
        setBounds(0, 0, 0, 0);
        setSize(610, 300);
        add(scrollMenu);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //starting threads:
        cmcpt.start();
        slt.start();

    }

    public void shiftLeft(int stepSize) {
        pmap.shiftLeft(stepSize);
    }


    @Override
    public void setMouthClosedPerc(int mouthClosedPerc) {
        pmap.setMouthClosedPerc(mouthClosedPerc);
    }

    public void displayErrorLog(){

        cmcpt.stopRunning();
        slt.stopRunning();

        Main.LaunchErrorLogWindow();

        this.dispose();

    }

}

