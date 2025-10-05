import javax.swing.*;

public class Main {

    //the whole program uses given semantics:
    //j, n, x, ...width - x-axis
    //i, m, y, ...height - y-axis

    public static void main(String[] args) {

        //addTestHighScores();
        addTestErrors();

        LaunchMenu();

    }

    public static void LaunchMenu(){ SwingUtilities.invokeLater(() -> new MenuWindow()); }
    public static void LaunchGame(int gameWidth, int gameHeight){ SwingUtilities.invokeLater(()-> new GameWindow(gameWidth, gameHeight)); }
    public static void LaunchGameSettings(){ SwingUtilities.invokeLater(() -> new GameSettingsWindow());}
    public static void LaunchHighScores(){
        SwingUtilities.invokeLater(() -> new HighScoresWindow());
    }
    public static void LaunchErrorLogWindow(){ SwingUtilities.invokeLater(() -> new ErrorLogWindow());}
    public static void LaunchEndScreen(
            int highScore, String hs0text, String hs1text, String hs2text, String hs3text, String hs4text, String hs5text, String hs0icon, String hs1icon,
            String hs2icon, String hs3icon, String hs4icon, String hs5icon){
        SwingUtilities.invokeLater(() -> new GameEndWindow(highScore, hs0text, hs1text, hs2text, hs3text, hs4text,hs5text, hs0icon,hs1icon, hs2icon, hs3icon, hs4icon, hs5icon ));
    }

    private static void addTestHighScores(){

        try {
            HighScoresReadAndWrite.addNewHighScore("20;Marc");
            HighScoresReadAndWrite.addNewHighScore("31;Penny");
            HighScoresReadAndWrite.addNewHighScore("43;Tom");
            HighScoresReadAndWrite.addNewHighScore("78;Ann");
        } catch (Exception ex){
            ExceptionLog.addError(ex.getMessage());
        }

    }

    private static void addTestErrors(){

        ExceptionLog.addError("error uno");
        ExceptionLog.addError("error dos");
        ExceptionLog.addError("error tres");

    }

}