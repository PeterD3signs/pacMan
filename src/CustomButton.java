import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomButton extends JButton {

    private final int minXsize;
    private final int minYsize;
    private Image img = null;
    private Image rollOverImg = null;
    private boolean imageReadSuccessful = true;

    public CustomButton(String iconPath, String rollOverIconPath, String backupText, int xSize, int ySize, int minXsize, int minYsize){

        this.minXsize = minXsize;
        this.minYsize = minYsize;

        try {
            img = ImageIO.read( getClass().getResource( iconPath ) );
            rollOverImg = ImageIO.read( getClass().getResource( rollOverIconPath ) );
        } catch (IOException ex){
            imageReadSuccessful = false;
            ExceptionLog.addError(ex.getMessage());
        }

        if(imageReadSuccessful) {
            super.setIcon(new ImageIcon( img.getScaledInstance(xSize, ySize, Image.SCALE_SMOOTH)));
            super.setRolloverIcon(new ImageIcon( rollOverImg.getScaledInstance(xSize, ySize, Image.SCALE_SMOOTH )));
        }else
            super.setText( backupText );

        super.setMargin( new Insets(0, 0, 0, 0) );
        super.setBorder( null );

    }

    public void SetNewSize (double xSize){

        if (xSize > minXsize){

            if (imageReadSuccessful){

                super.setIcon(new ImageIcon( img.getScaledInstance((int)xSize, -1, Image.SCALE_SMOOTH)));
                super.setRolloverIcon(new ImageIcon( rollOverImg.getScaledInstance((int)xSize, -1, Image.SCALE_SMOOTH)));

            } else {

                super.setSize( (int)xSize, (int)xSize * 3 / 19 );

            }

        } else {

            if (imageReadSuccessful){

                super.setIcon(new ImageIcon( img.getScaledInstance(minXsize, -1, Image.SCALE_SMOOTH)));
                super.setRolloverIcon(new ImageIcon( rollOverImg.getScaledInstance(minXsize, -1, Image.SCALE_SMOOTH)));

            } else {

                super.setSize( minXsize, minYsize );

            }

        }



    }

}
