package com.example.coen275ecommerce;

import db.SelectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    Button signupButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView brandingImgView;

    @FXML
    private ImageView lockImgView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField enterPasswordField;

    @FXML
    private BorderPane loginPane;

    @FXML
    private CheckBox isAdminCheckBox;

    private static String pageUsername;

    public static Map<String, String> users = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users.put("Jackson", "1234");
        users.put("Mark", "5678");
        users.put("Bob", "6789");
        File brandingFile = new File("src/main/resources/img/login/loginMain.png");
        Image brandingImg = new Image(brandingFile.toURI().toString());
        brandingImgView.setImage(brandingImg);

        File lockFile = new File("src/main/resources/img/login/lock.png");
        Image lockImg = new Image(lockFile.toURI().toString());
        lockImgView.setImage(lockImg);
    }

    public void loginButtonOnAction(ActionEvent event){
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            validateLogin();
        }else{
            loginMessageLabel.setText("Please enter username and password!");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void validateLogin(){
        try {
            String curUserName = usernameTextField.getText();
            String curPassword = enterPasswordField.getText();
            int admin = isAdminCheckBox.isSelected() ? 1 : 0;
            User user = new User();
            user.setUsername(curUserName);
            user.setPassword(curPassword);
            user.setUserType(admin);
            int count = SelectDB.selectUserCount(user);
            if(count>0){
                loginMessageLabel.setText("Welcome!");
                pageUsername = curUserName;
                MainPage(curUserName, admin);
            }else{
                loginMessageLabel.setText("Incorrect username or password or admin type. Please try again!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void MainPage(String username, int admin){
        try{
            if(admin == 0){
                FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("mainPage.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
                stage.setTitle("COEN 275, Group 3, E-Commerce: "+username);
                stage.setScene(scene);
                stage.show();
                loginPane.getScene().getWindow().hide();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("adminPage.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
                stage.setTitle("COEN 275, Group 3, E-Commerce-Admin Page: "+username);
                stage.setScene(scene);
                stage.show();
                loginPane.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void signupButtonOnAction(ActionEvent event) {
        createAccount();
    }

    private void createAccount(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("signupPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            stage.setTitle("COEN 275, Group 3, E-Commerce");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            loginPane.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public static String getPageName(){
        return pageUsername;
    }
}
