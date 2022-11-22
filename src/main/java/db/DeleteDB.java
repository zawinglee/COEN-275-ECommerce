package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DeleteDB {

    public static void deleteProdInCart(String username, String prodName){
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            String sql = "DELETE  FROM Shopping_Cart "+ "WHERE USER_NAME = "+"'"+ username +"'" +" AND "+"PROD_NAME = "+"'"+ prodName+"'"+";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void deleteForPlaceOrder(String username){
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            String sql = "DELETE  FROM Shopping_Cart "+ "WHERE USER_NAME = "+"'"+ username +"'" +";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records delete successfully");
    }


    public static void deleteProdInSystem(String prodName, String adminName){
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            String sql = "DELETE FROM Product "+ "WHERE name = "+"'"+ prodName +"'" +" AND "+"adminName = "+"'"+ adminName+"'"+";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records delete successfully");
    }
}
