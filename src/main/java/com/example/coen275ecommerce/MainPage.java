package com.example.coen275ecommerce;

import db.SelectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML private AnchorPane mainPageAnchor;
    @FXML private AnchorPane leftPanel;

    @FXML private Button electronBtn, shoeBtn, clothesBtn, bagsBtn, foodBtn, vehicleBtn, logoutButton, shoppingCartButton, ordersButton;

    @FXML private AnchorPane electronPanel, shoePanel, clothesPanel, bagsPanel, foodPanel, vehiclePanel;
    @FXML private FlowPane electronFlowPane, shoeFlowPane, clothesFlowPane, bagsFlowPane, foodFlowPane, vehicleFlowPane;

    private VBox singleProductUI;

    private ArrayList<Product> electronics, shoes, clothes, bags, food, vehicles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        electronics = new ArrayList<Product>(productGenerator("electronic"));
        shoes = new ArrayList<Product>(productGenerator("shoes"));
        clothes = new ArrayList<Product>(productGenerator("clothes"));
        bags = new ArrayList<Product>(productGenerator("bags"));
        food = new ArrayList<Product>(productGenerator("food"));
        vehicles = new ArrayList<Product>(productGenerator("vehicles"));
        try{
            for (Product elec : electronics) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                singleProductUI = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(elec);
                electronFlowPane.getChildren().add(singleProductUI);
            }

            for (Product shoe : shoes) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                singleProductUI = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(shoe);
                shoeFlowPane.getChildren().add(singleProductUI);
            }

            for (Product cloth : clothes) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                singleProductUI = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(cloth);
                clothesFlowPane.getChildren().add(singleProductUI);
            }

            for (Product bag : bags) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                singleProductUI = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(bag);
                bagsFlowPane.getChildren().add(singleProductUI);
            }

            for (Product f : food) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                singleProductUI = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(f);
                foodFlowPane.getChildren().add(singleProductUI);
            }

            for (Product v : vehicles) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                singleProductUI = fxml.load();
                ProductController productController = fxml.getController();
                productController.configureUI(v);
                vehicleFlowPane.getChildren().add(singleProductUI);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    private ArrayList<Product> productGenerator(String category) {
        // select all product with electronic type
        ArrayList<Product> res = SelectDB.selectProdWithProductType(category);
        for(Product prod:res){
            List<CustomerReview> list = SelectDB.selectReviewWithProdId(prod.getId());
            for(CustomerReview review:list){
                prod.addCustomerReview(review);
            }
        }
        return res;
    }

    @FXML
    private void onCategorySelected(ActionEvent event) {
        if (event.getSource() == electronBtn) {
            electronPanel.setVisible(true);
            shoePanel.setVisible(false);
            clothesPanel.setVisible(false);
            bagsPanel.setVisible(false);
            foodPanel.setVisible(false);
            vehiclePanel.setVisible(false);
        } else if (event.getSource() == shoeBtn) {
            electronPanel.setVisible(false);
            shoePanel.setVisible(true);
            clothesPanel.setVisible(false);
            bagsPanel.setVisible(false);
            foodPanel.setVisible(false);
            vehiclePanel.setVisible(false);
        } else if (event.getSource() == clothesBtn) {
            electronPanel.setVisible(false);
            shoePanel.setVisible(false);
            clothesPanel.setVisible(true);
            bagsPanel.setVisible(false);
            foodPanel.setVisible(false);
            vehiclePanel.setVisible(false);
        } else if (event.getSource() == bagsBtn) {
            electronPanel.setVisible(false);
            shoePanel.setVisible(false);
            clothesPanel.setVisible(false);
            bagsPanel.setVisible(true);
            foodPanel.setVisible(false);
            vehiclePanel.setVisible(false);
        } else if (event.getSource() == foodBtn) {
            electronPanel.setVisible(false);
            shoePanel.setVisible(false);
            clothesPanel.setVisible(false);
            bagsPanel.setVisible(false);
            foodPanel.setVisible(true);
            vehiclePanel.setVisible(false);
        } else if (event.getSource() == vehicleBtn) {
            electronPanel.setVisible(false);
            shoePanel.setVisible(false);
            clothesPanel.setVisible(false);
            bagsPanel.setVisible(false);
            foodPanel.setVisible(false);
            vehiclePanel.setVisible(true);
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
        Stage stage = (Stage) shoppingCartButton.getScene().getWindow();
        stage.close();

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
