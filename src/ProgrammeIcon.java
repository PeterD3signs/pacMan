import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ProgrammeIcon {

    public static Image getIcon(){

        Image img = null;

        try {
            img = ImageIO.read( ProgrammeIcon.class.getResource( "resources/Icon.png" ) );
        } catch (IOException ex){
            ExceptionLog.addError(ex.getMessage());
        }

        return img;

    }

}
