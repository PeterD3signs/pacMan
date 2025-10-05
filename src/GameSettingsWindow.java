import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameSettingsWindow extends JFrame {

    int gameWidth = 20;
    int gameHeight = 20;

    public GameSettingsWindow(){

        //Adding Panels:
        JPanel sizeSelectionPanel = new JPanel();
        sizeSelectionPanel.setLayout( new BoxLayout(sizeSelectionPanel, BoxLayout.Y_AXIS) );

        JPanel gswPanel = new JPanel();     //game settings window panel
        gswPanel.setLayout( new BorderLayout() );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new FlowLayout() );

        JPanel textFlowPanelWidth = new JPanel();
        textFlowPanelWidth.setLayout( new FlowLayout());



        CustomImageLabel mapWidthTextPanel = new CustomImageLabel("resources/setMapWidthText.png", "Set map width:", 30, 190, 30, 114, true);
        CustomImageLabel snw0 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 30, true);    //selected number width
        CustomImageLabel snw1 = new CustomImageLabel("resources/number2.png", "2", 30, 110, 30, 30, true);
        CustomImageLabel snw2 = new CustomImageLabel("resources/number0.png", "0", 30, 110, 30, 30, true);

        mapWidthTextPanel.addKeyListener( new CtrlShiftQListener() );
        snw0.addKeyListener( new CtrlShiftQListener() );
        snw1.addKeyListener( new CtrlShiftQListener() );
        snw2.addKeyListener( new CtrlShiftQListener() );

        textFlowPanelWidth.add( mapWidthTextPanel );
        textFlowPanelWidth.add(snw0);
        textFlowPanelWidth.add(snw1);
        textFlowPanelWidth.add(snw2);

        JPanel textFlowPanelHeight = new JPanel();
        textFlowPanelHeight.setLayout( new FlowLayout());

        CustomImageLabel mapHeightTextPanel = new CustomImageLabel("resources/setMapHeightText.png", "Set map height:", 30, 190, 30, 114, true);
        CustomImageLabel snh0 = new CustomImageLabel("resources/numberNull.png", " ", 30, 110, 30, 30, true);    //selected number height
        CustomImageLabel snh1 = new CustomImageLabel("resources/number2.png", "2", 30, 110, 30, 30, true);
        CustomImageLabel snh2 = new CustomImageLabel("resources/number0.png", "0", 30, 110, 30, 30, true);

        mapHeightTextPanel.addKeyListener( new CtrlShiftQListener() );
        snh0.addKeyListener( new CtrlShiftQListener() );
        snh1.addKeyListener( new CtrlShiftQListener() );
        snh2.addKeyListener( new CtrlShiftQListener() );

        textFlowPanelHeight.add( mapHeightTextPanel );
        textFlowPanelHeight.add(snh0);
        textFlowPanelHeight.add(snh1);
        textFlowPanelHeight.add(snh2);


        //Adding sliders:
        JSlider mapWidth = new JSlider( JSlider.HORIZONTAL, 10, 100, 20 );
        JSlider mapHeight = new JSlider( JSlider.HORIZONTAL, 10, 100, 20 );

        mapWidth.setMajorTickSpacing(10);
        mapWidth.setMinorTickSpacing(5);
        mapWidth.setPaintTicks(true);
        mapWidth.setPaintLabels(true);
        mapWidth.addKeyListener( new CtrlShiftQListener() );

        mapHeight.setMajorTickSpacing(10);
        mapHeight.setMinorTickSpacing(5);
        mapHeight.setPaintTicks(true);
        mapHeight.setPaintLabels(true);
        mapHeight.addKeyListener( new CtrlShiftQListener() );


        //Adding slider listeners:
        mapWidth.addChangeListener(e -> {

            JSlider mapWidth1 = (JSlider)e.getSource();
            int widthValue = mapWidth1.getValue();

            gameWidth = widthValue;

            if (widthValue == 100){

                snw0.ChangeImage("resources/number1.png", "1");
                snw1.ChangeImage("resources/number0.png", "0");
                snw2.ChangeImage("resources/number0.png", "0");

            } else {

                char ch1 = Integer.toString(widthValue).charAt(0);
                char ch2 = Integer.toString(widthValue).charAt(1);

                snw0.ChangeImage(("resources/numberNull.png"), " ");
                snw1.ChangeImage(("resources/number" +  ch1 + ".png"), ch1 + "");
                snw2.ChangeImage(("resources/number" +  ch2 + ".png"), ch2 + "");

            }

        });
        mapHeight.addChangeListener(e -> {

            JSlider mapHeight1 = (JSlider)e.getSource();
            int heightValue = mapHeight1.getValue();

            gameHeight = heightValue;

            if (heightValue == 100){

                snh0.ChangeImage("resources/number1.png", "1");
                snh1.ChangeImage("resources/number0.png", "0");
                snh2.ChangeImage("resources/number0.png", "0");

            } else {

                char ch1 = Integer.toString(heightValue).charAt(0);
                char ch2 = Integer.toString(heightValue).charAt(1);

                snh0.ChangeImage(("resources/numberNull.png"), " ");
                snh1.ChangeImage(("resources/number" +  ch1 + ".png"), ch1 + "");
                snh2.ChangeImage(("resources/number" +  ch2 + ".png"), ch2 + "");

            }

        });


        //Adding buttons:
        CustomButton play = new CustomButton("resources/playButton.png", "resources/playRollOverButton.png",  "Play", 190, 30, 114, 18);
        CustomButton exit = new CustomButton("resources/exitButton.png", "resources/exitRollOverButton.png",  "Exit", 190, 30, 114, 18);

        play.addKeyListener( new CtrlShiftQListener() );
        exit.addKeyListener( new CtrlShiftQListener() );


        //Adding button listeners:
        play.addActionListener(e -> {

            Main.LaunchGame(gameWidth, gameHeight);

            Container tempFrame = play.getParent();

            while ( !(tempFrame instanceof JFrame)  )
                tempFrame = tempFrame.getParent();

            ((JFrame)tempFrame).dispose();

        });

        exit.addActionListener(e -> {

            Main.LaunchMenu();

            Container tempFrame = exit.getParent();

            while ( !(tempFrame instanceof JFrame)  )
                tempFrame = tempFrame.getParent();

            ((JFrame)tempFrame).dispose();

        });


        //Adding buttons to buttonPanel:
        buttonPanel.add(play);
        buttonPanel.add(exit);


        //Adding component scaling:
        sizeSelectionPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                play.SetNewSize(((double) sizeSelectionPanel.getWidth() - 40) / 3);
                exit.SetNewSize(((double) sizeSelectionPanel.getWidth() - 40) / 3);

            }
        });
        textFlowPanelHeight.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                mapHeightTextPanel.SetNewSize((double) textFlowPanelHeight.getWidth() / 2);

                snh0.SetNewSize((double) textFlowPanelHeight.getWidth() / 20);
                snh1.SetNewSize((double) textFlowPanelHeight.getWidth() / 20);
                snh2.SetNewSize((double) textFlowPanelHeight.getWidth() / 20);

            }
        });
        textFlowPanelWidth.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                mapWidthTextPanel.SetNewSize((double) textFlowPanelWidth.getWidth() / 2);

                snw0.SetNewSize((double) textFlowPanelWidth.getWidth() / 20);
                snw1.SetNewSize((double) textFlowPanelWidth.getWidth() / 20);
                snw2.SetNewSize((double) textFlowPanelWidth.getWidth() / 20);

            }
        });


        //Setting Layout:
        sizeSelectionPanel.add( textFlowPanelWidth );
        sizeSelectionPanel.add( mapWidth );
        sizeSelectionPanel.add( textFlowPanelHeight );
        sizeSelectionPanel.add( mapHeight );
        sizeSelectionPanel.add( buttonPanel, BorderLayout.LINE_END);


        JScrollPane jScrollPane = new JScrollPane(sizeSelectionPanel);
        add(jScrollPane);


        //JFrame settings:
        setIconImage( ProgrammeIcon.getIcon() );
        setTitle("PAC-MAN");
        pack();
        setSize(610, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

}
