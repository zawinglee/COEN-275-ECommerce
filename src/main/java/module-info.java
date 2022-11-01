module com.example.coen275ecommerce {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.coen275ecommerce to javafx.fxml;
    exports com.example.coen275ecommerce;
}