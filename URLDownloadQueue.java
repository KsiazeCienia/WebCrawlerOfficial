import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Marcin on 11.12.2016.
 */
public class URLDownloadQueue implements DownloadQueue {

    LinkedList<URL> urlLinkedList;

    URLDownloadQueue() {
        urlLinkedList = new LinkedList<>();
    }

    @Override
    public void addPage(URL pageURL) {
        urlLinkedList.add(pageURL);
    }

    @Override
    public URL getNextPage() {
        if (!urlLinkedList.isEmpty()) {
            return urlLinkedList.remove();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return urlLinkedList.isEmpty();
    }

}
