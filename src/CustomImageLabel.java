import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomImageLabel extends JLabel {

    private final int minXsize;
    private final int bakcupFontSize;
    private Image img = null;
    private String iconPath;
    private String backupText;
    private boolean imageReadSuccessful = true;

    public CustomImageLabel(String iconPath, String backupText, int backupFontSize, int xSize, int ySize, int minXsize, boolean scaledByX){

        this.minXsize = minXsize;
        this.bakcupFontSize = backupFontSize;
        this.iconPath = iconPath;
        this.backupText = backupText;

        try {
            img = ImageIO.read( getClass().getResource( iconPath ) );
        } catch (IOException ex){
            imageReadSuccessful = false;
            ExceptionLog.addError(ex.getMessage());
        }

        if(imageReadSuccessful) {
            super.setIcon(new ImageIcon( img.getScaledInstance(xSize, ( scaledByX ? -1 : ySize), Image.SCALE_SMOOTH)));
        }else
            super.setText( backupText );

    }

    public void SetNewSize (double xSize){

        if (xSize > minXsize){

            if (imageReadSuccessful){

                super.setIcon(new ImageIcon( img.getScaledInstance((int)xSize, -1, Image.SCALE_SMOOTH)));

            } else {

                super.setFont( new Font( Font.SANS_SERIF, Font.BOLD, bakcupFontSize ) );

            }

        } else {

            if (imageReadSuccessful){

                super.setIcon(new ImageIcon( img.getScaledInstance(minXsize, -1, Image.SCALE_SMOOTH)));

            } else {

                super.setFont( new Font( Font.SANS_SERIF, Font.BOLD, bakcupFontSize ) );

            }

        }



    }

    public void ChangeImage (String iconPath, String backupText){

        this.iconPath = iconPath;
        this.backupText = backupText;

        try {
            img = ImageIO.read( getClass().getResource(iconPath) );
        } catch (IOException ex){
            imageReadSuccessful = false;
            ExceptionLog.addError(ex.getMessage());
        }

        if(imageReadSuccessful) {
            super.setIcon(new ImageIcon( img.getScaledInstance(super.getWidth(), super.getHeight(), Image.SCALE_SMOOTH)));
        }else
            super.setText( backupText );


    }

    public String getImagePath(){
        return iconPath;
    }

    public String getImageBackupText(){
        return backupText;
    }



}
