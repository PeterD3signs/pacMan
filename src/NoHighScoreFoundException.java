public class NoHighScoreFoundException extends Exception{

    public NoHighScoreFoundException() {
        super("High score with given parameters was not found. Nothing was deleted.");
    }

}
