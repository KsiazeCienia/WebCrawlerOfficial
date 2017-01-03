import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Marcin on 11.12.2016.
 */
public class VisitedPagesQueue implements VisitedPages {

    LinkedList<URL> urlLinkedList;

    VisitedPagesQueue() {
        urlLinkedList = new LinkedList<>();
    }

    @Override
    public boolean pageAlreadyVisited(URL pageURL) {
        return urlLinkedList.contains(pageURL);
    }

    @Override
    public void addVisitedPage(URL pageURL) {
        urlLinkedList.add(pageURL);
    }
}
