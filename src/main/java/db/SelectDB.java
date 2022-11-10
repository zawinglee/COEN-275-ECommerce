package db;
import com.example.coen275ecommerce.ProductInCart;

import java.sql.*;


public class SelectDB {
    public static void main( String args[])
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

    public static ProductInCart selectProdFromCart(String username, String prodName) {
        ProductInCart prod = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT * FROM Shopping_Cart WHERE USER_NAME = "  + "'" + username + "'" + " AND PROD_NAME = " + "'" + prodName + "'"
                    + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                prod = new ProductInCart(resultSet.getString("PROD_NAME"), resultSet.getString("DESCRIPTION"), resultSet.getString("PRICE"), resultSet.getInt("QUANTITY"), resultSet.getString("TOTAL_PRICE"));
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return prod;
    }

    public static int selectProdFromCart(String username) {
        int count = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT count(*) FROM Shopping_Cart WHERE USER_NAME = "  + "'" + username + "'" + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return count;
    }
}
