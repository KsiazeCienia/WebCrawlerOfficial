/**
 * Created by Marcin on 27.11.2016.
 */

/**
 * interfejs mechanizu logów
 */
public interface LoggerInterafece {

    /**
     * typ wyliczeniowy zawierający wszystkie możliwe typy komunikatków
     */
    enum Type {
     INFO, WARRNING, ERROR
    }

    /**
     * Metoda pobierająca jaki typ komunikatu i o jakiej treści ma zostac utworzony
     * @param type typ komunikatu
     * @param message tresc komunikatu
     */
    void log(Type type, String message);

    /**
     * Metoda tworząca komunkiat typu INFO i podanej treści
     * @param message treść komunkatu
     */
    void info(String message);

    /**
     * Metoda tworząca komunkiat typu WARRNING i podanej treści
     * @param message treść komunkatu
     */
    void warrning(String message);

    /**
     * Metoda tworząca komunkiat typu ERROR i podanej treści
     * @param message treść komunkatu
     */
    void error(String message);

}
