package com.example.coen275ecommerce;

import db.CreateDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminOrderController implements Initializable {
    @FXML
    TableView<ProductInCart> orderTableView;

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
    TableColumn<ProductInCart, String> orderTime;

    @FXML
    Pane ordersPane;

    @FXML
    Button backToAdminButton;

    private ObservableList<ProductInCart> productList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }


    public void backToAdminButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) backToAdminButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("adminPage.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            mainStage.setScene(scene);
            mainStage.show();
            ordersPane.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }



    public ObservableList<ProductInCart> getProductOrderHistory(String prodName){
        Connection connection = CreateDB.getConnection();
        String query = "SELECT * FROM Orders WHERE PROD_NAME = " +"'" + prodName+"' ;";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            ProductInCart product;
            while (resultSet.next()) {
                product = new ProductInCart(resultSet.getString("PROD_NAME"), resultSet.getString("DESCRIPTION"), resultSet.getString("PRICE"), resultSet.getInt("QUANTITY"), resultSet.getString("TOTAL_PRICE"));
                String orderTime = resultSet.getString("ORDER_TIME");
                product.setOrderTime(orderTime);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void showProducts(){
        productList = FXCollections.observableArrayList();
        for(Product prod:AdminPageController.getProductList()){
            getProductOrderHistory(prod.getTitle());
        }

        productName.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("name"));
        productDescription.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("description"));
        productPrice.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("price"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<ProductInCart, Integer>("quantity"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("totalPrice"));
        orderTime.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("orderTime"));
        orderTableView.setItems(productList);
    }

}
