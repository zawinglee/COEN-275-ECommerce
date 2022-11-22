package com.example.coen275ecommerce;

import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private CheckBox asUserCheckBox;

    @FXML
    private CheckBox asAdminCheckBox;

    @FXML
    private Pane signupPane;

    Map<String, String> users = LoginController.users;

    private int isAdmin;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("src/main/resources/img/signup/shield.png");
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
        boolean asUser = asUserCheckBox.isSelected(), asAdmin = asAdminCheckBox.isSelected();
        isAdmin = asAdmin ? 1 : 0;
        if(asUser && asAdmin) {
            usernameCheckLabel.setText("");
            passwordMatchLabel.setText("");
            signupMessageLabel.setText("You can not sign up as user and administrator simultaneously.");
        }
        else {
            if(usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false
                    && confirmPasswordTextField.getText().isBlank()==false && (asUser || asAdmin)) {
                String curUsername = usernameTextField.getText();
                if(checkUserName(curUsername)) {
                    passwordMatchLabel.setText("");
                    signupMessageLabel.setText("");
                    usernameCheckLabel.setText("This Username already exists. Please try another one.");
                }
                else {
                    if (passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
                        registerUser();
                        passwordMatchLabel.setText("");
                        usernameCheckLabel.setText("");
                        signupMessageLabel.setText("Sign Up Successfully!");
                    }
                    else{
                        usernameCheckLabel.setText("");
                        signupMessageLabel.setText("");
                        passwordMatchLabel.setText("Password Doesn't Match. Please try again.");
                    }
                }
            }
            else{
                usernameCheckLabel.setText("");
                signupMessageLabel.setText("");
                passwordMatchLabel.setText("Please fill all details.");
            }
        }
    }

    public boolean checkUserName(String userName){
        Connection c;
        Statement stmt = null;
        int count = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT count(*) FROM User WHERE userName = "  + "'" + userName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while ( rs.next() ) {
                count=rs.getInt(1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        if(count>0){
            return true;
        }
        return false;
    }

    public void registerUser(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        users.put(username, password);
        User user = new User();
        user.setUserType(isAdmin);
        user.setUsername(username);
        user.setPassword(password);
        insertUser(user);
    }

    public static void insertUser(User user) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO User (userName, password, isAdmin) " +
                    "VALUES (" + "'" + user.getUsername() + "'" + "," + "'" + user.getPassword() + "'" + ","  + String.valueOf(user.getUserType()) +  ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }


//    @FXML

}
