package com.example.coen275ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private ImageView shieldImgView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Button signupButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Label usernameCheckLabel;

    @FXML
    private Label signupMessageLabel;

    @FXML
    private Label passwordMatchLabel;

    @FXML
    private Pane signupPane;

    Map<String, String> users = LoginController.users;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("/Users/mac/COEN-275-ECommerce/src/main/resources/img/signup/shield.png");
        Image shieldImg = new Image(shieldFile.toURI().toString());
        shieldImgView.setImage(shieldImg);
    }

    public void goBackButtonOnAction(ActionEvent event){
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try{
            //on Cancel it will load the Login page back on

            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("loginPage.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();
            signupPane.getScene().getWindow().hide();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void signupButtonOnAction(ActionEvent event){

        if(usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false && confirmPasswordTextField.getText().isBlank()==false) {
            String curUsername = usernameTextField.getText();
            if(users.containsKey(curUsername)) usernameCheckLabel.setText("This Username already exists. Please try another one.");
            else {
                if (passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
                    registerUser();
                    signupMessageLabel.setText("Sign Up Successfully!");
                }
                else{
                    usernameCheckLabel.setText("");
                    passwordMatchLabel.setText("Password Doesn't Match. Please try again.");
                }
            }
        }
        else{
            passwordMatchLabel.setText("Please fill all details.");
        }

    }

    public void registerUser(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        users.put(username, password);
    }




//    @FXML

}
