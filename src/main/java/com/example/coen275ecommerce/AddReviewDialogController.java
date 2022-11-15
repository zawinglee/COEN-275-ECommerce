package com.example.coen275ecommerce;

import db.InsertDB;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

public class AddReviewDialogController {
    @FXML private Slider numberRating;
    @FXML private TextField textRating;
    @FXML private Label numberRatingLabel;

    private String customerUsername;
    private int productID;
    private Stage stage;

    @FXML private void doneBtnAction() {
        CustomerReview review = new CustomerReview(this.customerUsername,  numberRating.getValue(), textRating.getText() , this.productID);
        InsertDB.insertReview(review);
        numberRating.setValue(0);
        textRating.setText("");
        stage.close();
    }

    public void configure(Stage dialogStage, String username, int productID) {
        this.stage = dialogStage;
        this.customerUsername = username;
        this.productID = productID;
    }

    public void changeLabelHandler(javafx.scene.input.MouseEvent mouseEvent) {
        numberRatingLabel.setText(String.format("%1.1f", numberRating.getValue()) + " / 5.0");
    }
}
