module com.example.coen275ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.coen275ecommerce.controller to javafx.fxml;
    exports com.example.coen275ecommerce;
}