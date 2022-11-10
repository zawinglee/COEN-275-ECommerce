package com.example.coen275ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class placeOrderController implements Initializable {
    @FXML
    Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(LoginController.getPageName() + "!");
    }
}
