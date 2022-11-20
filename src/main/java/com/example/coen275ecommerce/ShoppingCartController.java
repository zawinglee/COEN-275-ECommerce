package com.example.coen275ecommerce;

import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable {
    @FXML
    TableView<ProductInCart> cartTableView;

    @FXML
    TableColumn<ProductInCart, String> productName;

    @FXML
    TableColumn<ProductInCart, String> productDescription;

    @FXML
    TableColumn<ProductInCart, String> productPrice;

    @FXML
    TableColumn<ProductInCart, Integer> productQuantity;

    @FXML
    TableColumn<ProductInCart, String> totalPrice;

    @FXML
    Button deleteButton;

    @FXML
    Button placeOrderButton;

    @FXML
    Button continueShoppingButton;

    @FXML
    Pane shoppingCartPane;

    @FXML
    TextField deletedQuantityTextField;

    @FXML
    TextField deletedProdNameTextField;

    @FXML
    Label deleteMssgLabel;

    @FXML
    Label placeOrderMssgLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }

    public ObservableList<ProductInCart> getProductsInCart(){
        ObservableList<ProductInCart> productInCartList = FXCollections.observableArrayList();
        Connection connection = CreateDB.getConnection();
        String username = LoginController.getPageName();
        String query = "SELECT * FROM Shopping_Cart WHERE USER_NAME = " +"'" + username+"' ;";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            ProductInCart product;
            while (resultSet.next()) {
                product = new ProductInCart(resultSet.getString("PROD_NAME"), resultSet.getString("DESCRIPTION"), resultSet.getString("PRICE"), resultSet.getInt("QUANTITY"), resultSet.getString("TOTAL_PRICE"));
                productInCartList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productInCartList;
    }

    public void showProducts(){
        ObservableList<ProductInCart> productList = getProductsInCart();

        productName.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("name"));
        productDescription.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("description"));
        productPrice.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("price"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<ProductInCart, Integer>("quantity"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("totalPrice"));

        cartTableView.setItems(productList);

        cartTableView.setRowFactory(tv -> {
            TableRow<ProductInCart> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    ProductInCart rowData = row.getItem();
                    deletedProdNameTextField.setText(rowData.getName());
                    deletedQuantityTextField.setText(String.valueOf(rowData.getQuantity()));
                }
            });
            return row ;
        });
    }

    public void continueShoppingButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) continueShoppingButton.getScene().getWindow();
        stage.close();

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("mainPage.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            mainStage.setScene(scene);
            mainStage.show();
            shoppingCartPane.getScene().getWindow().hide();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void deleteButtonOnAction(ActionEvent event) {
        String username = LoginController.getPageName();
        String prodName = deletedProdNameTextField.getText();
        String quantityString = deletedQuantityTextField.getText();
        if(prodName.isBlank() || quantityString.isBlank()) {
            deleteMssgLabel.setText("Please input entire information.");
            return;
        }
        ProductInCart product = SelectDB.selectProdFromCart(username, prodName);
        if(product == null){
            deleteMssgLabel.setText("This product is not in your shopping cart");
        }else {
            int count = product.getQuantity();
            int quantity = Integer.parseInt(quantityString);
            if(quantity == count) {
                DeleteDB.deleteProdInCart(username, prodName);
                showProducts();
            }
            else if(quantity < count) {
                product.setQuantity(count - quantity);
                String priceString = product.getPrice();
                int signIndex = priceString.indexOf("$");
                int price = Integer.parseInt(priceString.substring(signIndex + 1));
                product.setTotalPrice("$"+(product.getQuantity() * price));
                UpdateDB.updateProdInCart(username, product);
                showProducts();
            }else{
                deleteMssgLabel.setText("Please input correct quantity.");
            }
        }
    }

    public void placeOrderOnAction(ActionEvent event) {
        String username = LoginController.getPageName();
        int prodCount = SelectDB.selectProdFromCart(username);
        if(prodCount == 0) {
            placeOrderMssgLabel.setText("Oops... Your cart is empty.");
            return;
        }
        List<ProductInCart> productInCartList = SelectDB.selectProdListFromCart(username);
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTime = dateFormat.format(now);
        for (ProductInCart product : productInCartList) InsertDB.insertProdToOrders(username, product, orderTime);
        for (ProductInCart shopProduct : productInCartList) {
            Product product = SelectDB.selectProdResultFromSystemWithProdName(shopProduct.getName());
            UpdateDB.updateProdWithPlaceOrder(shopProduct.getName(), product.getQuantity() - shopProduct.getQuantity());
        }
        DeleteDB.deleteForPlaceOrder(username);
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("placeOrderPage.fxml"));
            Stage placeOrderStage = new Stage();
            placeOrderStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            placeOrderStage.setScene(scene);
            placeOrderStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        showProducts();
    }

}
