package com.example.coen275ecommerce;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.util.Objects;

public class CustomerReviewController {
    @FXML
    Label author;
    @FXML
    Label numericRating;
    @FXML
    Label textRating;

    public void configureUI(CustomerReview review) {
        author.setText(review.getAuthorUsername());
        numericRating.setText(String.valueOf(review.getNumericRating()) + " / 5.0");
        textRating.setText(review.getTextRating());
    }
}
