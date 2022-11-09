package com.example.coen275ecommerce;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ProductDetailPageController {
    @FXML
    private ImageView imgSource;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label price;
    @FXML
    private Label soldBy;
    @FXML
    private Label starRating;

    public void configureUI(Product product) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSource())));
        imgSource.setImage(image);
        title.setText(product.getTitle());
        price.setText("$" + String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        soldBy.setText("Sold By: " + product.getOwnBy());
        starRating.setText("Overall Rating: " + String.valueOf(product.getStarRating()) + " / 5.0");
    }
}
