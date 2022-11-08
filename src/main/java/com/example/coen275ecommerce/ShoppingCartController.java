package com.example.coen275ecommerce;

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
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable {
    @FXML
    TableView<ProductInCart> cartTableView;

    @FXML
    TableColumn<ProductInCart, Integer> productId;

    @FXML
    TableColumn<ProductInCart, String> productName;

    @FXML
    TableColumn<ProductInCart, String> productDescription;

    @FXML
    TableColumn<ProductInCart, Integer> productPrice;

    @FXML
    TableColumn<ProductInCart, Integer> productQuantity;

    @FXML
    TableColumn<ProductInCart, Integer> totalPrice;

    @FXML
    Button deleteButton;

    @FXML
    Button placeOrderButton;

    @FXML
    Button continueShoppingButton;

    @FXML
    Pane shoppingCartPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ObservableList<ProductInCart> getProductsInCart(){
        ObservableList<ProductInCart> productInCartList = FXCollections.observableArrayList();
        return productInCartList;
    }

    public void showProducts(){
        ObservableList<ProductInCart> productList = getProductsInCart();

        productId.setCellValueFactory(new PropertyValueFactory<ProductInCart, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("name"));
        productDescription.setCellValueFactory(new PropertyValueFactory<ProductInCart, String>("description"));
        productPrice.setCellValueFactory(new PropertyValueFactory<ProductInCart, Integer>("price"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<ProductInCart, Integer>("quantity"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<ProductInCart, Integer>("totalPrice"));

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
}
