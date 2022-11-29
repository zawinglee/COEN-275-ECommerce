package com.example.coen275ecommerce;

import db.DeleteDB;
import javafx.event.ActionEvent;
import db.InsertDB;
import db.SelectDB;
import db.UpdateDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class ProductController {
    @FXML
    private ImageView productImage;

    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel, cartQuantityLabel;
    @FXML
    private Label availableLabel;
    @FXML
    private Button addToCartButton;
    @FXML
    private Button minusButton;
    int inCartCount;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void configureUI(Product product) {
        setProduct(product);
        Image image = new Image(new File("src/main/resources/" + product.getImageSource()).toURI().toString());
        productImage.setImage(image);
        nameLabel.setText(product.getTitle());
        priceLabel.setText("$" + String.valueOf(product.getPrice()));
        descriptionLabel.setText(product.getDescription());
        availableLabel.setText("available: " + String.valueOf(product.getQuantity()));
        ProductInCart prodInCart = SelectDB.selectProdFromCart(LoginController.getPageName(), product.getTitle());
        inCartCount = prodInCart == null ? 0 : prodInCart.getQuantity();
        cartQuantityLabel.setText(inCartCount +"");
    }

    public void addToCartButtonOnAction(ActionEvent event) {
        inCartCount++;
        cartQuantityLabel.setText(inCartCount +"");
        String prod_name = nameLabel.getText();
        String description = descriptionLabel.getText();
        String priceString = priceLabel.getText();

        int signIndex = priceString.indexOf("$");
        int price = Integer.parseInt(priceString.substring(signIndex+1));
        int quantity = Integer.parseInt(cartQuantityLabel.getText());
        int totalPrice = price * quantity;
        String totalPriceString = "$"+totalPrice;
        ProductInCart product = new ProductInCart(prod_name, description, priceString, quantity, totalPriceString);
        String curUsername = LoginController.getPageName();
        if(inCartCount == 1) {
            InsertDB.insertProdToCart(curUsername, product);
        }else if(inCartCount >= 2){
            UpdateDB.updateProdInCart(curUsername, product);
        }

    }

    public void minusButtonOnAction(ActionEvent event) {
        String prod_name = nameLabel.getText();
        String priceString = priceLabel.getText();
        String curUsername = LoginController.getPageName();


        ProductInCart product = SelectDB.selectProdFromCart(curUsername, prod_name);
        if(product == null){
            return;
        }else {
            int quantityInCart = product.getQuantity();
            if(quantityInCart == 1) {
                DeleteDB.deleteProdInCart(curUsername, prod_name);
            }
            else if(quantityInCart > 1) {
                product.setQuantity(quantityInCart - 1);
                int signIndex = priceString.indexOf("$");
                int price = Integer.parseInt(priceString.substring(signIndex + 1));
                product.setTotalPrice("$"+(product.getQuantity() * price));
                UpdateDB.updateProdInCart(curUsername, product);
            }
        }

        inCartCount--;
        cartQuantityLabel.setText(inCartCount +"");
    }

    @FXML private void showProductDetailPage() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("productDetailPage.fxml"));
            Parent root = fxml.load();
            ProductDetailPageController detailPageController = fxml.getController();
            detailPageController.configureUI(getProduct());
            Scene dialogScene = new Scene(root,1300, 700);
            dialog.setScene(dialogScene);
            dialog.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
