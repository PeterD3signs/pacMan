import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

public class HighScoresWindow extends JFrame{

    public HighScoresWindow() {

        int minButtonX = 114;
        int minButtonY = 18;
        Vector<String> HSVector = new Vector<>();

        JPanel highScores = new JPanel();
        highScores.setLayout( new BorderLayout() );

        JPanel buttonSpace = new JPanel();
        buttonSpace.setLayout( new FlowLayout() );

        CustomImageLabel topText = new CustomImageLabel("resources/highScoresText.png", "High Scores:", 30, 190, 30, minButtonX, true);
        topText.setHorizontalAlignment( SwingConstants.CENTER );



        try {
            HSVector = HighScoresReadAndWrite.getHighScores();
        } catch (Exception ex){
            ExceptionLog.addError(ex.getMessage());
        }


        HighScoresListModel hslm = new HighScoresListModel( HSVector );
        JList dataList = new JList();
        dataList.setModel( hslm );
        HighScoresListRenderer hslr = new HighScoresListRenderer( 16);
        dataList.setCellRenderer( hslr );

        CustomButton RemoveHS = new CustomButton("resources/removeHSButton.png", "resources/removeHSRollOverButton.png",  "Remove HS", 190, 30, minButtonX, minButtonY);
        CustomButton toMenu = new CustomButton("resources/ToMenuButton.png", "resources/ToMenuRollOverButton.png",  "To menu", 190, 30, minButtonX, minButtonY);

        toMenu.addActionListener(e -> {

            Main.LaunchMenu();

            Container tempFrame = toMenu.getParent();

            while ( !(tempFrame instanceof JFrame)  )
                tempFrame = tempFrame.getParent();

            ((JFrame)tempFrame).dispose();

        });

        RemoveHS.addActionListener(e -> {

            int index = dataList.getSelectedIndex();
            if (index >= 0) {

                try {
                    HighScoresReadAndWrite.removeHighScore(hslm.getElementAt(index).toString());
                } catch (Exception ex){
                    ExceptionLog.addError(ex.getMessage());
                }

                hslm.remove(index);



            }

        });

        //Adding listeners for CtrlShiftQ:
        RemoveHS.addKeyListener( new CtrlShiftQListener() );
        toMenu.addKeyListener( new CtrlShiftQListener() );
        dataList.addKeyListener( new CtrlShiftQListener() );

        //Adding buttons:
        buttonSpace.add(RemoveHS);
        buttonSpace.add(toMenu);

        //Adding button scaling:
        highScores.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                RemoveHS.SetNewSize(((double) highScores.getWidth() - 40) / 3);
                toMenu.SetNewSize(((double) highScores.getWidth() - 40) / 3);

            }
        });

        //Adding topText scaling:
        topText.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                topText.SetNewSize((double) topText.getWidth() / 2);

            }
        });

        //Adding dataList font scaling:
        dataList.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                dataList.setFixedCellHeight(dataList.getWidth() / 30);
                hslr.changeFontSize(dataList.getWidth() / 30);

            }
        });

        //Preparing the layout:
        highScores.add(topText, BorderLayout.PAGE_START);
        highScores.add(buttonSpace, BorderLayout.PAGE_END);
        highScores.add(dataList, BorderLayout.CENTER);
        JScrollPane scrollHighScores = new JScrollPane( highScores );

        add(scrollHighScores);

        setIconImage( ProgrammeIcon.getIcon() );
        setTitle("PAC-MAN");
        setSize(610, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

}
