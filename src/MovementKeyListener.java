import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementKeyListener implements KeyListener {

    private final GameWindow gameWindow;
    public MovementKeyListener(GameWindow gameWindow){
        this.gameWindow = gameWindow;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP)
            gameWindow.updateKeyPressed(Directions.Up);

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            gameWindow.updateKeyPressed(Directions.Down);

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            gameWindow.updateKeyPressed(Directions.Right);

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            gameWindow.updateKeyPressed(Directions.Left);


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
