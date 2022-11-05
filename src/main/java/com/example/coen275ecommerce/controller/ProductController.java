package com.example.coen275ecommerce.controller;

import com.example.coen275ecommerce.entity.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ProductController {
    @FXML
    private ImageView productImage;

    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label price;



    public void configure(Product product) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSource())));
        productImage.setImage(image);
        title.setText(product.getTitle());
        price.setText("$" + String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
    }
}
