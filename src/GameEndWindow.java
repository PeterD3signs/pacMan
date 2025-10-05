import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameEndWindow extends JFrame {

    int currentMenuX = 900;
    int currentMenuY = 400;
    int buttonX = 190;       //original graphics for menu buttons are in scale 19 x 3
    int buttonY = 30;
    int minButtonX = 114;
    int minButtonY = 18;
    boolean HSSaved;
    int highScore;

    public GameEndWindow(int highScore, String hs0text, String hs1text, String hs2text, String hs3text, String hs4text, String hs5text, String hs0icon, String hs1icon, String hs2icon, String hs3icon, String hs4icon, String hs5icon) {

        HSSaved = false;
        this.highScore = highScore;

        boolean[] visibleHsDigit = new boolean[6];
        visibleHsDigit[0] = !(hs0text.isEmpty() || hs0text.equals(" "));
        visibleHsDigit[1] = !(hs1text.isEmpty() || hs1text.equals(" "));
        visibleHsDigit[2] = !(hs2text.isEmpty() || hs2text.equals(" "));
        visibleHsDigit[3] = !(hs3text.isEmpty() || hs3text.equals(" "));
        visibleHsDigit[4] = !(hs4text.isEmpty() || hs4text.equals(" "));
        visibleHsDigit[5] = !(hs5text.isEmpty() || hs5text.equals(" "));

        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        JPanel buttonSpace = new JPanel();
        buttonSpace.setLayout(new FlowLayout());

        JPanel YHSPanel = new JPanel();
        YHSPanel.setLayout(new FlowLayout());

        JPanel highScorePanel = new JPanel();
        highScorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel enterNamePanel = new JPanel();
        enterNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel scoreAndName = new JPanel();
        scoreAndName.setLayout(new BorderLayout());

        //Adding labels:
        CustomImageLabel highScoreLabel = new CustomImageLabel("resources/YourHighScoreText.png", "Your high score:", 30, 190, 30, 114, true);

        CustomImageLabel hs0 = new CustomImageLabel(hs0icon, hs0text, 30, 110, 30, 10, true);
        CustomImageLabel hs1 = new CustomImageLabel(hs1icon, hs1text, 30, 110, 30, 10, true);
        CustomImageLabel hs2 = new CustomImageLabel(hs2icon, hs2text, 30, 110, 30, 10, true);
        CustomImageLabel hs3 = new CustomImageLabel(hs3icon, hs3text, 30, 110, 30, 10, true);
        CustomImageLabel hs4 = new CustomImageLabel(hs4icon, hs4text, 30, 110, 30, 10, true);
        CustomImageLabel hs5 = new CustomImageLabel(hs5icon, hs5text, 30, 110, 30, 10, true);

        //Adding textField:
        JTextField enterName = new JTextField();
        enterName.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
        enterName.setText("Enter your nickname");

        //Adding buttons:
        CustomButton ToMenuButton = new CustomButton("resources/ToMenuButton.png", "resources/ToMenuRollOverButton.png", "To menu", buttonX, buttonY, minButtonX, minButtonY);
        CustomButton SaveScore = new CustomButton("resources/SaveHSButton.png", "resources/SaveHSRollOverButton.png", "Save score", buttonX, buttonY, minButtonX, minButtonY);

        //Adding action listeners:
        ToMenuButton.addActionListener(e -> {

            Main.LaunchMenu();

            Container tempFrame = ToMenuButton.getParent();

            while (!(tempFrame instanceof JFrame))
                tempFrame = tempFrame.getParent();

            ((JFrame) tempFrame).dispose();

        });

        SaveScore.addActionListener(e -> {

            if (enterName.getText().equals("Enter your nickname") || enterName.getText().equals("Enter nickname before saving your HS")){

                enterName.setText("Enter nickname before saving your HS");

            } else {

                if (!HSSaved){

                    saveHS(enterName.getText());
                    enterName.setText("High score saved!");
                    enterName.setEditable(false);

                }

            }

        });

        ToMenuButton.addKeyListener( new CtrlShiftQListener() );
        SaveScore.addKeyListener( new CtrlShiftQListener() );
        enterName.addKeyListener( new CtrlShiftQListener() );


        //Setting layout:
        highScorePanel.add(hs0);
        highScorePanel.add(hs1);
        highScorePanel.add(hs2);
        highScorePanel.add(hs3);
        highScorePanel.add(hs4);
        highScorePanel.add(hs5);

        buttonSpace.add(ToMenuButton);
        buttonSpace.add(SaveScore);

        YHSPanel.add(highScoreLabel);

        enterNamePanel.add(enterName);

        scoreAndName.add(highScorePanel, BorderLayout.PAGE_START);
        scoreAndName.add(enterNamePanel, BorderLayout.CENTER);

        //Adding scaling:
        menu.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                ToMenuButton.SetNewSize(((double) menu.getWidth() - 40) / 3);
                SaveScore.SetNewSize(((double) menu.getWidth() - 40) / 3);

                hs0.SetNewSize( visibleHsDigit[0] ? ((double)highScorePanel.getWidth() / 10 - 1) : 0 );
                hs1.SetNewSize( visibleHsDigit[1] ? ((double)highScorePanel.getWidth() / 10 - 1) : 0 );
                hs2.SetNewSize( visibleHsDigit[2] ? ((double)highScorePanel.getWidth() / 10 - 1) : 0 );
                hs3.SetNewSize( visibleHsDigit[3] ? ((double)highScorePanel.getWidth() / 10 - 1) : 0 );
                hs4.SetNewSize( visibleHsDigit[4] ? ((double)highScorePanel.getWidth() / 10 - 1) : 0 );
                hs5.SetNewSize( visibleHsDigit[5] ? ((double)highScorePanel.getWidth() / 10 - 1) : 0 );

                highScoreLabel.SetNewSize(((double) YHSPanel.getWidth() / 2));

                enterName.setPreferredSize( new Dimension((int)((float)scoreAndName.getWidth() / 1.1), scoreAndName.getHeight() / 3) );
                enterName.setFont( new Font(Font.SANS_SERIF, Font.BOLD, scoreAndName.getWidth() / 30 ) );

            }
        });

        //Further setting layout:
        menu.add(YHSPanel, BorderLayout.PAGE_START);
        menu.add(scoreAndName, BorderLayout.CENTER);
        menu.add(buttonSpace, BorderLayout.PAGE_END);

        JScrollPane scrollMenu = new JScrollPane(menu);

        setIconImage( ProgrammeIcon.getIcon() );
        setTitle("PAC-MAN");
        setBounds(0, 0, 0, 0);
        setSize(currentMenuX, currentMenuY);
        add(scrollMenu);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void saveHS(String nickname){

        try {
            HighScoresReadAndWrite.addNewHighScore(highScore + ";" + nickname);
        } catch (Exception ex) {
            ExceptionLog.addError(ex.getMessage());
        }

    }



}

