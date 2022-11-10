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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Button logoutButton;

    @FXML
    TextField productNameTextField;

    @FXML
    TextField priceTextField;

    @FXML
    TextField quantityTextField;

    @FXML
    TextArea descriptionTextField;

    @FXML
    Label sellerPageMsg;

    @FXML
    TableView<Product> productTableView;

    @FXML
    TableColumn<Product, String> productName;

    @FXML
    TableColumn<Product, Integer> price;

    @FXML
    TableColumn<Product, Integer> quantity;

    @FXML
    TableColumn<Product, String> description;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }

    public ObservableList<Product> getProductsInSystem(){
        ObservableList<Product> productList = FXCollections.observableArrayList();
        Connection connection = CreateDB.getConnection();
        String username = LoginController.getPageName();
        String query = "SELECT * FROM Product WHERE adminName = " +"'" + username +"' ;";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            Product product;
            while (resultSet.next()) {
                product = new Product();
                product.setTitle(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setOwnBy(resultSet.getString("adminName"));
                product.setDescription(resultSet.getString("description"));
                productList.add(product);
                System.out.println(product.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void showProducts(){
        ObservableList<Product> productList = getProductsInSystem();

        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        productTableView.setItems(productList);
    }

    public void logoutButtonOnAction(ActionEvent event){
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("loginPage.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addProductAction(ActionEvent event){
        String prodName = productNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();
        String description = descriptionTextField.getText();
        if(prodName.length() == 0 || price.length() == 0 || quantity.length() == 0 || description.length() == 0){
            sellerPageMsg.setText("Failed to insert! Adding product should fill out every information field.");
            return;
        }
        InsertDB.insertProdToSystem(prodName, Integer.valueOf(price), Integer.valueOf(quantity), description, "empty",  LoginController.getPageName());
        sellerPageMsg.setText("Successfully insert!");
        showProducts();
    }

    public void updateProductAction(ActionEvent event){
        String prodName = productNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();
        String description = descriptionTextField.getText();
        int prodCount = SelectDB.selectProdFromSystem(prodName,LoginController.getPageName());
        if(prodCount == 0){
            sellerPageMsg.setText("This is not your product, please retry to edit again");
            return;
        }
        Product prod = SelectDB.selectProdResultFromSystem(prodName,LoginController.getPageName());
        if(price.length() != 0){
            prod.setPrice(Integer.valueOf(price));
        }
        if(quantity.length() != 0){
            prod.setQuantity(Integer.valueOf(quantity));
        }
        if(description.length() != 0){
            prod.setDescription(description);
        }
        UpdateDB.updateProdInSystem(prod);
        sellerPageMsg.setText("Update successfully!");
        showProducts();
    }

    public void deleteProductionAction(ActionEvent event){
        String prodName = productNameTextField.getText();
        int prodCount = SelectDB.selectProdFromSystem(prodName,LoginController.getPageName());
        if(prodCount == 0){
            sellerPageMsg.setText("This is not your product, please retry to delete again");
            return;
        }
        DeleteDB.deleteProdInSystem(prodName, LoginController.getPageName());
        sellerPageMsg.setText("Delete successfully!");
        showProducts();
    }
}
