package com.example.coen275ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML private AnchorPane mainPageAnchor;
    @FXML private AnchorPane leftPanel;

    @FXML private Button electronBtn, menBtn, womenBtn, logoutButton, shoppingCartButton, ordersButton;

    @FXML private AnchorPane electronPanel, menPanel, womenPanel;
    @FXML private VBox electronVBox;
    @FXML private GridPane electronGridPane;

    private ArrayList<Product> electronics;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        electronics = new ArrayList<Product>(generate_electronics());
        try{
            for (Product elec : electronics) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                electronVBox = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(elec);

                electronGridPane.add(electronVBox, 0, 0);
                GridPane.setMargin(electronVBox, new Insets(10));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    private ArrayList<Product> generate_electronics() {
        ArrayList<Product> res = new ArrayList<>();

        Product product = new Product();

        product.setImageSource("/img/electronic/iphone14pro.png");
        product.setTitle("Apple iPhone 14 Pro");
        product.setPrice(1299);
        product.setDescription("the latest iPhone!");
        product.setId(1);
        product.setQuantity(999);
        product.setStarRating(5.0);
        product.setOwnBy("Apple Inc.");
        product.addCustomerReview(new CustomerReview("Abc", 4.8, "good good", 1));
        product.addCustomerReview(new CustomerReview("GGG", 1.8, "bad bad", 1));
        product.addCustomerReview(new CustomerReview("CC", 3.8, "so so", 1));
        product.addCustomerReview(new CustomerReview("DD", 3.9, "not bad", 1));

        res.add(product);
        return res;
    }

    @FXML
    private void onCategorySelected(ActionEvent event) {
        if (event.getSource() == electronBtn) {
            electronPanel.setVisible(true);
            menPanel.setVisible(false);
            womenPanel.setVisible(false);
        } else if (event.getSource() == menBtn) {
            electronPanel.setVisible(false);
            menPanel.setVisible(true);
            womenPanel.setVisible(false);
        } else if (event.getSource() == womenBtn) {
            electronPanel.setVisible(false);
            menPanel.setVisible(false);
            womenPanel.setVisible(true);
        }
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
            mainPageAnchor.getScene().getWindow().hide();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void shoppingCartButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) shoppingCartButton.getScene().getWindow();
        stage.close();

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("shoppingCart.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void ordersButtonOnAction(ActionEvent event){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("orderpage.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
