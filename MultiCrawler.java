import java.net.URL;

/**
 * Created by Marcin on 03.01.2017.
 */
public class MultiCrawler extends Thread {
    VisitedPages visitedPages;
    DownloadQueue downloadQueue;
    Logger logger;
    int numerWatku;

    MultiCrawler (VisitedPages visitedPages, DownloadQueue downloadQueue, Logger logger, int numerWatku) {
        this.visitedPages = visitedPages;
        this.downloadQueue = downloadQueue;
        this.logger = logger;
        this.numerWatku = numerWatku;
    }

    @Override
    public void run() {
        WebCrawler webCrawler = new WebCrawler();
        PageDownloader pageDownloader = new PageDownloader();
        URL urlToVisit = null;
        while(true) {
            synchronized (downloadQueue) {
                while (!downloadQueue.isEmpty()) {
                    URL url = downloadQueue.getNextPage();
                    synchronized (visitedPages) {
                        if (!visitedPages.pageAlreadyVisited(url)) {
                            urlToVisit = url;
                            visitedPages.addVisitedPage(urlToVisit);
                            break;
                        }
                    }
                }
            }
            try {
                if (urlToVisit != null) {
                    webCrawler.findURL(pageDownloader.downloadPage(urlToVisit.toString()),
                            visitedPages, downloadQueue);
                    logger.log(LoggerInterafece.Type.INFO, urlToVisit.toString() + " numer watku " + numerWatku);
                }
            } catch (DownloaderException e) {
                System.out.println("Unable to get HTML");
            }
        }
    }
}
