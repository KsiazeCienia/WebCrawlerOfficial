import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marcin on 11.12.2016.
 */
public class PageDownloader implements WWWPageDownloader {

    @Override
    public String downloadPage(String pageURL) throws DownloaderException {
        try {
            URL url  = new URL(pageURL);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder stringBuilder = new StringBuilder();
                while ( bufferedReader.readLine() != null) {
                    stringBuilder.append(bufferedReader.readLine() + "\n");
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                throw new DownloaderException("Unable to connect!");
            }
        } catch (MalformedURLException e) {
            throw new DownloaderException("Page doesn't exist!");
        }
    }
}
