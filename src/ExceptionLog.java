import java.util.LinkedList;

public class ExceptionLog {

    private static final LinkedList<String> errors = new LinkedList<>();

    public static void addError (String error){
        errors.add(error);
    }
    public static String getErrors(){

        StringBuilder sb = new StringBuilder();

        for (String error : errors) {
            sb.append(error);
            sb.append("\n\n");
        }

        return sb.toString();

    }

}
