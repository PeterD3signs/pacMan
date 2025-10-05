import javax.swing.*;
import java.util.Vector;

public class HighScoresListModel extends AbstractListModel {

    private Vector<String> highScores;

    public HighScoresListModel(Vector<String> highScores ){

        this.highScores = highScores;

    }

    @Override
    public int getSize() {
        return highScores.size();
    }

    @Override
    public Object getElementAt(int index) {
        return highScores.get( index );
    }

    public void remove(int index){
        highScores.remove( index );
        fireIntervalRemoved(this, index, index);
    }

}
