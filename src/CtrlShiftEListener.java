import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CtrlShiftEListener implements KeyListener {

    //needs to be used on a component

    private boolean CtrlPressed = false;
    private boolean ShiftPressed = false;
    private boolean EPressed = false;
    private final MenuWindow mw;

    public CtrlShiftEListener(MenuWindow mw) {

        this.mw = mw;

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            CtrlPressed = true;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            ShiftPressed = true;

        if (e.getKeyCode() == KeyEvent.VK_E)
            EPressed = true;


        if (EPressed && ShiftPressed && CtrlPressed){

            mw.displayErrorLog();

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            CtrlPressed = false;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            ShiftPressed = false;

        if (e.getKeyCode() == KeyEvent.VK_E)
            EPressed = false;

    }

}
