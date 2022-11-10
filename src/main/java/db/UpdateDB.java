package db;

import com.example.coen275ecommerce.ProductInCart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateDB {


    public static void updateProdInCart(String username, ProductInCart product) {
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String prodName = product.getName();
            int quantity = product.getQuantity();
            String totalPrice = product.getTotalPrice();
            String sql = "UPDATE Shopping_Cart "+
                            "SET QUANTITY = " + quantity +", "+
                            "TOTAL_PRICE = "+ "'" + totalPrice + "'" +
                            " WHERE USER_NAME = "+"'"+ username +"'" +" AND "+"PROD_NAME = "+"'"+ prodName+"'"+";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Update Production in cart successfully");
    }
}
