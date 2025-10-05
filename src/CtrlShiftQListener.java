import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CtrlShiftQListener implements KeyListener {

    //needs to be used on a component

    private boolean CtrlPressed = false;
    private boolean ShiftPressed = false;
    private boolean QPressed = false;
    private final CloseOpenedThreadsAfterCtrlShiftQ hostFrame;

    public CtrlShiftQListener(){
        hostFrame = null;
    }

    public CtrlShiftQListener(CloseOpenedThreadsAfterCtrlShiftQ hostFrame){
        this.hostFrame = hostFrame;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            CtrlPressed = true;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            ShiftPressed = true;

        if (e.getKeyCode() == KeyEvent.VK_Q)
            QPressed = true;


        if (QPressed && ShiftPressed && CtrlPressed){

            if (hostFrame != null)
                hostFrame.closeThreads();

            Main.LaunchMenu();

            Component tempFrame = (Component)e.getSource();

            while ( !(tempFrame instanceof JFrame)  )
                tempFrame = tempFrame.getParent();

            ((JFrame)tempFrame).dispose();

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            CtrlPressed = false;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            ShiftPressed = false;

        if (e.getKeyCode() == KeyEvent.VK_Q)
            QPressed = false;

    }

}
