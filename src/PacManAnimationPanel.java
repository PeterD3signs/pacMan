import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PacManAnimationPanel extends JPanel implements MouthClosedPercPassing {

    private int sideLength;
    private int commonY;
    private int pmX;    //pac man
    PaintPacMan pacMan;
    private boolean justStarted = true;

    public PacManAnimationPanel(int startingSideLength, int startingVertSpace){

        sideLength = startingSideLength;
        commonY = this.getHeight() * 2 / 3;
        pmX = sideLength;

        pacMan = new PaintPacMan(Color.YELLOW, sideLength / 10, sideLength / 50, 0 , Directions.Left.toString(), pmX, commonY, sideLength - 20, startingVertSpace);
        add(pacMan);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                if (!justStarted){

                    revalidate();

                    PacManAnimationPanel pmap = (PacManAnimationPanel)e.getComponent();

                    int newSideLength = pmap.getWidth();
                    commonY = pmap.getHeight() / 3;

                    pmX = (int)(((double)pmX / (double)sideLength) * (double)newSideLength);
                    pacMan.changeSize(pmX , commonY,newSideLength / 10, newSideLength / 50, newSideLength - 20, pmap.getHeight());

                    sideLength = pmap.getWidth();

                }

            }
        });

        setLayout( new FlowLayout( FlowLayout.LEADING ) );

        setBounds(0, 0, 0, 0);
        setBackground( Color.LIGHT_GRAY );
        setVisible(true);

        justStarted = false;

    }

    public void shiftLeft(int StepLength){

        pmX = pmX - StepLength;
        if (pmX < - sideLength / 10 - 1)
            pmX = sideLength;
        pacMan.setPmX(pmX);

        revalidate();

    }

    @Override
    public void setMouthClosedPerc(int mouthClosedPerc) {

        pacMan.setMouthClosedPerc(mouthClosedPerc);

    }

}
