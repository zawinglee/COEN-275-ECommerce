package com.example.coen275ecommerce;

import db.CreateDB;
import db.InsertDB;
import db.SelectDB;
import db.UpdateDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.util.Objects;

public class ProductController {
    @FXML
    private ImageView productImage;

    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel, quantityLabel;
    @FXML
    private Button addToCartButton;
    int count;
    public void configureUI(Product product) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSource())));
        productImage.setImage(image);
        nameLabel.setText(product.getTitle());
        priceLabel.setText("$" + String.valueOf(product.getPrice()));
        descriptionLabel.setText(product.getDescription());
        ProductInCart prodInCart = SelectDB.selectProdFromCart(LoginController.getPageName(), product.getTitle());
        count = prodInCart == null ? 0 : prodInCart.getQuantity();
        quantityLabel.setText(count+"");
    }

    public void addToCartButtonOnAction(ActionEvent event) {
        count++;
        quantityLabel.setText(count+"");
        String prod_name = nameLabel.getText();
        String description = descriptionLabel.getText();
        String priceString = priceLabel.getText();

        int signIndex = priceString.indexOf("$");
        int price = Integer.parseInt(priceString.substring(signIndex+1));
        int quantity = Integer.parseInt(quantityLabel.getText());
        int totalPrice = price * quantity;
        String totalPriceString = "$"+totalPrice;
        ProductInCart product = new ProductInCart(prod_name, description, priceString, quantity, totalPriceString);
        String curUsername = LoginController.getPageName();
        if(count == 1) {
            InsertDB.insertProdToCart(curUsername, product);
        }else if(count >= 2){
            UpdateDB.updateProdInCart(curUsername, product);
        }

    }
}
