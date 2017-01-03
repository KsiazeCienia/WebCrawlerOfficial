import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcin on 28.11.2016.
 */
public class Logger implements LoggerInterafece {

    /**
     * tablica typów komunikatów jakie użytwknik chce wyświetlić
     */
    private Type[] type;

    /**
     * definiuje gdzie mają zostać wypisane komunkaty
     */
    private PrintWriter printWriter;

    /**
     * Konstruktor tworzący nową listę loggerów, na podstawie podanych typów
     * @param outputStream miejsce docelowe wypisania komunikatów
     * @param _type typ komunikatu, który ma być wypisany
     * @param types umożliwia wybranie większej ilosći typó komunikatów
     */
    public Logger(OutputStream outputStream,Type _type, Type ... types) {
        type = new Type[3];
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        } catch (Exception e) {
            System.out.println("Unable to initianalize output");
        }
        type[0] = _type;
        for ( int i = 0; i < types.length; i++){
            type[i+1] = types[i];
        }
    }


    @Override
    public void log(Type messageType, String message) {
        for ( int i = 0; i < type.length; i++) {
            if ((type[i] == Type.INFO)&&(type[i] == messageType)) {
                info(message);
                continue;
            }
            if ((type[i] == Type.WARRNING)&&(type[i] == messageType)) {
                warrning(message);
                continue;
            }
            if ((type[i] == Type.ERROR)&&(type[i] == messageType)) {
                error(message);
            }
        }
        printWriter.flush();
    }

    @Override
    public void info(String message) {
        printWriter.print("INFO " + createDate() + " : " + message + "\n");
    }

    @Override
    public void warrning(String message) {
        printWriter.print("WARRNING " + createDate() + " : " + message + "\n");
    }

    @Override
    public void error(String message) {
        printWriter.print("ERROR " + createDate() + " : " + message + "\n");
    }

    private String createDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(currentDate);
    }
}
