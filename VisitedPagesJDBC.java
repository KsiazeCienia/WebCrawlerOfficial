import java.net.URL;
import java.sql.*;

/**
 * Created by Marcin on 12.12.2016.
 */
public class VisitedPagesJDBC implements VisitedPages {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/testmarcwloc";
    private static final String DB_USER = "marcwloc";
    private static final String DB_PASSWD = "";


    Connection connection;

    VisitedPagesJDBC(Connection _connection) {
        connection = _connection;
    }

    @Override
    public void addVisitedPage(URL pageURL) {
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate("insert into lista_odwiedzonych (link) "
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

    @Override
    public boolean pageAlreadyVisited(URL pageURL) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("select count(*) from lista_odwiedzonych where link='" + pageURL +"'" );
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getInt(1) == 0) {
                return false;
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
        return true;
    }
}
