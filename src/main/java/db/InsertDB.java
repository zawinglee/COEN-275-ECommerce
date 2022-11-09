package db;

import com.example.coen275ecommerce.ProductInCart;
import com.example.coen275ecommerce.SignupController;

import java.sql.*;

public class InsertDB
{
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
            String name = "tom";
            String word = "123456";
            int admin = 0;
            String sql = "INSERT INTO User (userName, password, isAdmin) " +
                    "VALUES (" + "'" + name + "'" + "," + "'" + word + "'" + ","  + String.valueOf(admin) +  ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void insertProdToCart(String userName, ProductInCart product) {
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String prodName = product.getName();
            String description = product.getDescription();
            String price = product.getPrice();
            int quantity = product.getQuantity();
            String totalPrice = product.getTotalPrice();
            String sql = "INSERT INTO Shopping_Cart (USER_NAME,PROD_NAME, DESCRIPTION, PRICE, QUANTITY, TOTAL_PRICE) " +
                    "VALUES ("  + "'" + userName + "'" + ","
                                + "'" + prodName + "'" + ","
                                + "'" + description + "'" + ","
                                + "'" + price + "'" + ","
                                + "'" + quantity + "'" + ","
                                + "'"+ totalPrice + "'" +");";
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
}