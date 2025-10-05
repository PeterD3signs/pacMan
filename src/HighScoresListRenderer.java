import javax.swing.*;
import java.awt.*;

public class HighScoresListRenderer extends JLabel implements ListCellRenderer {

    private int fontSize;

    public HighScoresListRenderer(int fontSize){
        setOpaque(true);
        this.fontSize = fontSize;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setText( (index + 1 ) + ".   " + ((String) value).replace(";", " - ") );

        setFont( new Font( Font.SANS_SERIF, Font.BOLD, fontSize ));

        if (isSelected)
            setBackground(Color.LIGHT_GRAY);
        else
            setBackground(Color.WHITE);

        return this;
    }

    public void changeFontSize(int fontSize){

        this.fontSize = fontSize;

    }

}
