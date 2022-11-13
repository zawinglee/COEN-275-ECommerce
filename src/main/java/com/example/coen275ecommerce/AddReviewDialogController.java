package com.example.coen275ecommerce;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.w3c.dom.events.MouseEvent;

public class AddReviewDialogController {
    @FXML private Slider numberRating;
    @FXML private TextField textRating;
    @FXML private Label numberRatingLabel;

    String customerUsername;
    int productID;

    @FXML private void doneBtnAction() {
//        CustomerReview customerReview = new CustomerReview(this.customerUsername, numberRating.getValue(), textRating.getText(), this.productID);
        numberRating.setValue(0);
        textRating.setText("");
    }

    public void configure(String username, int productID) {
        this.customerUsername = username;
        this.productID = productID;
    }

    public void changeLabelHandler(javafx.scene.input.MouseEvent mouseEvent) {
        numberRatingLabel.setText(String.format("%1.1f", numberRating.getValue()) + " / 5.0");
    }
}
