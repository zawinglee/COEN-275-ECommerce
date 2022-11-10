package com.example.coen275ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    @FXML
    private VBox customersReviewVBox;
    @FXML
    private VBox customersReviewContainer;

    public void configureUI(Product product) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSource())));
        imgSource.setImage(image);
        title.setText(product.getTitle());
        price.setText("$" + String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        soldBy.setText("Sold By: " + product.getOwnBy());
        starRating.setText("Overall Rating: " + String.valueOf(product.getStarRating()) + " / 5.0");

        try {
            for (CustomerReview review : product.getCustomersTextReview()) {
                try {
                    FXMLLoader fxml = new FXMLLoader();
                    fxml.setLocation(getClass().getResource("customerReview.fxml"));
                    Parent reviewUI = fxml.load();
                    CustomerReviewController customerReviewController = fxml.getController();
                    customerReviewController.configureUI(review);
                    customersReviewContainer.getChildren().add(reviewUI);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
