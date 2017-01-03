import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcin on 11.12.2016.
 */
public class WebCrawler {

    public static final int NUMBER_OF_THREADS = 5;

    Logger logger = new Logger(System.out, LoggerInterafece.Type.INFO);
    VisitedPages visitedPagesQueue;
    DownloadQueue urlDownloadQueue;

    WebCrawler(Connection connection) {
        visitedPagesQueue = new VisitedPagesJDBC(connection);
        urlDownloadQueue = new URLDownloadJDBC(connection);
    }

    WebCrawler() {
        visitedPagesQueue = new VisitedPagesQueue();
        urlDownloadQueue = new URLDownloadQueue();
    }

    public static void main(String[] args) throws Exception {

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/testmarcwloc", "marcwloc", "");

        WebCrawler webCrawler = new WebCrawler();
        ArrayList<MultiCrawler> threadArrayList = new ArrayList<>();

        try {
            URL url = new URL("http://www.onet.pl");
            webCrawler.urlDownloadQueue.addPage(url);
            for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                MultiCrawler multiCrawler = new MultiCrawler(webCrawler.visitedPagesQueue, webCrawler.urlDownloadQueue,
                        webCrawler.logger, i);
                multiCrawler.start();
                threadArrayList.add(multiCrawler);
            }
            for(int i = 0; i < NUMBER_OF_THREADS; ++i)
            {
                threadArrayList.get(i).join();
            }
        } catch (MalformedURLException e) {
            System.out.println("Unable to connect");
        }
    }

    /**
     * Metoda otrzymuje jak argument strone w htmlu, wyszukuje w niej linki, sprawdza czy dany link jest już w kolejce odwiedzonych jak
     * nie to dodaje do lisrt do odwiedzenia
     * @param HTMLpage strona w HTMLu
     * @param visitedPagesQueue lista odwiedzonych stron
     * @param urlDownloadQueue lista stron do odwiedzenia
     */
    public void findURL(String HTMLpage, VisitedPages visitedPagesQueue, DownloadQueue urlDownloadQueue) {
        Pattern mainPattern = Pattern.compile("<[aA] [^>]* href=\"([^\"]+)\"");
        Matcher mainMatcher = mainPattern.matcher(HTMLpage);
        Pattern sidePattern = Pattern.compile("^http.*");
        Matcher sideMatcher;
        while (mainMatcher.find()) {
            sideMatcher = sidePattern.matcher(mainMatcher.group(1));
            while (sideMatcher.find()) {
                try {
                    URL tmp = new URL(sideMatcher.group(0));
                    if (!visitedPagesQueue.pageAlreadyVisited(tmp))
                        synchronized (urlDownloadQueue) {
                            urlDownloadQueue.addPage(tmp);
                        }
                } catch (MalformedURLException e) {
                    System.out.println("Unable to connect");
                }
            }
        }
    }
}


