package db;
import java.sql.*;


public class SelectDB {
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String userName = "lin";
            String password = "123";
            int isAdmin = 0;
            String sql = "SELECT count(*) FROM User WHERE userName = "  + "'" + userName + "'" + " AND password = " + "'" + password + "'"
                    + "AND isAdmin = " + String.valueOf(isAdmin);
            ResultSet rs = stmt.executeQuery( sql);
            int count = 0;
            while ( rs.next() ) {
                count=rs.getInt(1);
            }
            System.out.println(count);
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
