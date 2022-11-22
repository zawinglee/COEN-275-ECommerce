package db;
import com.example.coen275ecommerce.CustomerReview;
import com.example.coen275ecommerce.Product;
import com.example.coen275ecommerce.ProductInCart;
import com.example.coen275ecommerce.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SelectDB {
    public static void main( String args[])
    {

    }

    public static int selectUserCount(User user){
        int count = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT count(*) FROM User WHERE userName = "  + "'" + user.getUsername() + "'" + " AND password = " + "'" + user.getPassword() + "'"
                    + "AND isAdmin = " + String.valueOf(user.getUserType());
            ResultSet rs = stmt.executeQuery( sql);
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
        return count;
    }

    public static ProductInCart selectProdFromCart(String username, String prodName) {
        ProductInCart prod = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

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
        return prod;
    }

    public static List<ProductInCart> selectProdListFromCart(String username) {
        List<ProductInCart> list = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM Shopping_Cart WHERE USER_NAME = "  + "'" + username + "'" + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                ProductInCart prod = new ProductInCart(resultSet.getString("PROD_NAME"), resultSet.getString("DESCRIPTION"), resultSet.getString("PRICE"), resultSet.getInt("QUANTITY"), resultSet.getString("TOTAL_PRICE"));
                list.add(prod);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return list;
    }

    public static int selectProdFromCart(String username) {
        int count = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

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
        return count;
    }

    public static int selectProdFromSystem(String prodName, String adminName) {
        int count = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT count(*) FROM Product WHERE name = "  + "'" + prodName + "'" +
                    "AND adminName = "+ "'" + adminName + "'" + ";";
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
        return count;
    }

    public static Product selectProdResultFromSystem(String prodName, String adminName) {
        Product prod = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM Product WHERE name = "  + "'" + prodName + "'" +
                    "AND adminName = "+ "'" + adminName + "'" + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                prod = new Product();
                prod.setTitle(resultSet.getString("name"));
                prod.setPrice(resultSet.getInt("price"));
                prod.setQuantity(resultSet.getInt("quantity"));
                prod.setOwnBy(resultSet.getString("adminName"));
                prod.setDescription(resultSet.getString("description"));
                prod.setProductType(resultSet.getString("productType"));
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return prod;
    }

    public static Product selectProdResultFromSystemWithProdName(String prodName) {
        Product prod = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM Product WHERE name = "  + "'" + prodName + "'" + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                prod = new Product();
                prod.setTitle(resultSet.getString("name"));
                prod.setPrice(resultSet.getInt("price"));
                prod.setQuantity(resultSet.getInt("quantity"));
                prod.setOwnBy(resultSet.getString("adminName"));
                prod.setDescription(resultSet.getString("description"));
                prod.setProductType(resultSet.getString("productType"));
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return prod;
    }

    public static ArrayList<Product> selectProdWithProductType(String productType){
        Connection c = null;
        Statement stmt = null;
        ArrayList<Product> prodList = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "SELECT * FROM Product WHERE productType = "  + "'" + productType + "'" + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Product prod = new Product();
                prod.setTitle(resultSet.getString("name"));
                prod.setPrice(resultSet.getInt("price"));
                prod.setQuantity(resultSet.getInt("quantity"));
                prod.setOwnBy(resultSet.getString("adminName"));
                prod.setDescription(resultSet.getString("description"));
                prod.setProductType(resultSet.getString("productType"));
                prod.setId(resultSet.getInt("id"));
                prod.setStarRating(5.0);
                prod.setImageSource(resultSet.getString("image"));
//                Random ran = new Random();
//                int x = ran.nextInt(5) + 1;
//
//                if (resultSet.getString("productType").equals("electronic")) {
//                    prod.setImageSource("/img/electronic/electronic" + String.valueOf(x) + ".jpg");
//                } else if (resultSet.getString("productType").equals("bags")) {
//                    prod.setImageSource("/img/bags/bags" + String.valueOf(x) + ".jpg");
//                } else if (resultSet.getString("productType").equals("clothes")) {
//                    prod.setImageSource("/img/clothes/clothes" + String.valueOf(x) + ".jpg");
//                } else if (resultSet.getString("productType").equals("food")) {
//                    prod.setImageSource("/img/food/foods" + String.valueOf(x) + ".jpg");
//                } else if (resultSet.getString("productType").equals("shoes")) {
//                    prod.setImageSource("/img/shoes/shoes" + String.valueOf(x) + ".jpg");
//                } else if (resultSet.getString("productType").equals("vehicles")) {
//                    prod.setImageSource("/img/vehicle/vehicle" + String.valueOf(x) + ".jpg");
//                }
//                prod.addCustomerReview(new CustomerReview("Abc", 4.8, "good good", 1));
//                prod.addCustomerReview(new CustomerReview("GGG", 1.8, "bad bad", 1));
//                prod.addCustomerReview(new CustomerReview("CC", 3.8, "so so", 1));
//                prod.addCustomerReview(new CustomerReview("DD", 3.9, "not bad", 1));
                prodList.add(prod);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return prodList;
    }

    public static List<CustomerReview> selectReviewWithProdId(int productId) {
        List<CustomerReview> list = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM Review WHERE PRODUCT_ID = "  +  productId + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                CustomerReview customerReview = new CustomerReview(resultSet.getString("USER_NAME"),
                        resultSet.getDouble("RATING") ,  resultSet.getString("COMMENT"),
                        resultSet.getInt("PRODUCT_ID"));
                customerReview.setNumericRating((double) Math.round(customerReview.getNumericRating() * 100) / 100);
                list.add(customerReview);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return list;
    }
}
