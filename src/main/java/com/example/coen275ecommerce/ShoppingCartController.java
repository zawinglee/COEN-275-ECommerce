package com.example.coen275ecommerce;

import db.CreateDB;
import db.DeleteDB;
import db.SelectDB;
import db.UpdateDB;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }

    public ObservableList<ProductInCart> getProductsInCart(){
        ObservableList<ProductInCart> productInCartList = FXCollections.observableArrayList();
        Connection connection = CreateDB.getConnection();
        String query = "SELECT * FROM Shopping_Cart";
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
    }

    public void continueShoppingButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) continueShoppingButton.getScene().getWindow();
        stage.close();

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("mainPage.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.show();
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
            deleteMssgLabel.setText("Please input correct product name.");
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

}