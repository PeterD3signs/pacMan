import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ErrorLogWindow extends JFrame {

    public ErrorLogWindow() {

        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        JPanel buttonSpace = new JPanel();
        buttonSpace.setLayout(new FlowLayout());

        JPanel textAreaSpace = new JPanel();
        textAreaSpace.setLayout(new FlowLayout(FlowLayout.LEADING));

        JTextArea textArea = new JTextArea(ExceptionLog.getErrors());
        textArea.setFont(new Font( Font.SANS_SERIF, Font.BOLD, 20 ));
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);

        CustomButton ToMenu = new CustomButton("resources/ToMenuButton.png", "resources/ToMenuRollOverButton.png",  "To menu", 190, 30, 114, 18);

        ToMenu.addActionListener(e -> {

            Main.LaunchMenu();

            Container tempFrame = ToMenu.getParent();

            while (!(tempFrame instanceof JFrame))
                tempFrame = tempFrame.getParent();

            ((JFrame) tempFrame).dispose();

        });

        ToMenu.addKeyListener( new CtrlShiftQListener() );

        buttonSpace.add(ToMenu);
        textAreaSpace.add(textArea);

        menu.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                ToMenu.SetNewSize(((double) buttonSpace.getWidth() - 40) / 3);
                textArea.setFont( new Font( Font.SANS_SERIF, Font.BOLD, buttonSpace.getWidth() / 30 ) );
                textArea.setPreferredSize( new Dimension(textAreaSpace.getWidth() - 20, textAreaSpace.getHeight() / 2) );

            }
        });

        menu.add(textAreaSpace, BorderLayout.CENTER);
        menu.add(buttonSpace, BorderLayout.PAGE_END);

        JScrollPane scrollMenu = new JScrollPane(menu);

        setIconImage( ProgrammeIcon.getIcon() );
        setTitle("PAC-MAN");
        setBounds(0, 0, 0, 0);
        setSize(610, 900);
        add(scrollMenu);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

}
