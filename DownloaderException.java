/**
 * Created by Marcin on 11.12.2016.
 */
public class DownloaderException extends Exception {

    public DownloaderException() {
        super();
    }

    public DownloaderException(String message) {
        super(message);
    }

    public DownloaderException(Throwable cause) {
        super(cause);
    }

    public DownloaderException(String message, Throwable cause) {
        super(message,cause);
    }

}
