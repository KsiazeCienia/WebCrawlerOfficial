import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

/**
 * Created by Marcin on 12.12.2016.
 */
public class URLDownloadJDBC implements DownloadQueue {

    public static void main(String[] agrs) {

    }

    Connection connection;

    URLDownloadJDBC(Connection _connection) {
        connection = _connection;
    }

    @Override
    public URL getNextPage() {
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("SELECT TOP 1 * FROM lista_do_odwiedzenia");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            try {
                URL url = new URL(rs.getString("link"));
                return url;
            } catch (MalformedURLException e) {
                System.out.println("Page doesn't exist");
            } finally {
                deleteURL();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void addPage(URL pageURL) {
        if(pageURL.toString().length() < 400) {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("insert into lista_do_odwiedzenia (link) "
                        + "values( '" + pageURL + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("select count(*) from lista_do_odwiedzenia");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getInt(1) == 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void deleteURL() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("delete top (1) from lista_do_odwiedzenia");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

