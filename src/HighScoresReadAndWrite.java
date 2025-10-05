import java.io.*;
import java.util.Vector;

public class HighScoresReadAndWrite {

    //EVERY HIGH SCORE IS IN GIVEN FORMAT: "xxx;yyy" where x represents the high score and y represents the name of the player

    public static Vector<String> readHighSores() throws Exception{

        ObjectInputStream ois = null;

        Vector<String> highScores = new Vector<>();

        try {

            ois = new ObjectInputStream(new FileInputStream("src/HighScores.txt"));

            highScores = (Vector<String>) ois.readObject();

        } catch (EOFException ex) {

            setHighScores(new Vector<>());

        } finally {
            if (ois != null)
                ois.close();
        }

        return highScores;

    }

    public static void addNewHighScore(String HS) throws Exception{

        Vector<String> highScores = readHighSores();

        highScores.add(HS);

        setHighScores(highScores);

    }

    public static void removeHighScore(String HS) throws Exception{

        Vector<String> highScores = readHighSores();

        int index = highScores.indexOf(HS);

        if (index == -1){

            throw new NoHighScoreFoundException();

        } else {

            highScores.remove(HS);
            setHighScores(highScores);

        }

    }

    public static void setHighScores(Vector<String> highScores) throws Exception{

        ObjectOutputStream oos = null;

        try {

            oos = new ObjectOutputStream( new FileOutputStream( "src/HighScores.txt" , false ));

            oos.writeObject( highScores );

        } finally {
            if (oos != null)
                oos.close();
        }

    }

    public static Vector<String> getHighScores () throws Exception{

        Vector<String> highScores = readHighSores();



        if (highScores.size() > 1)
            highScores.sort((h1, h2) -> (Integer.compare(Integer.parseInt( h2.substring(0, h2.indexOf(";")) ), Integer.parseInt( h1.substring(0, h1.indexOf(";"))))));

        return highScores;

    }

    public static int getHighestScore () throws Exception{

        Vector<String> highScores = getHighScores();

        String highestScore = ( highScores.isEmpty() ? "0;" : highScores.get(0));

        return Integer.parseInt( highestScore.substring(0, highestScore.indexOf(';')) );

    }

}
