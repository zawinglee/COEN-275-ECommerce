package com.example.coen275ecommerce;

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
    private CheckBox isAdmin;

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
            int isAdmin = 0;
            int count = 0;
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:test.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                String sql = "SELECT count(*) FROM User WHERE userName = "  + "'" + curUserName + "'" + " AND password = " + "'" + curPassword + "'"
                        + "AND isAdmin = " + String.valueOf(isAdmin);
                ResultSet rs = stmt.executeQuery( sql);
                while ( rs.next() ) {
                    count=rs.getInt(1);
                }
                System.out.println(count);
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Operation done successfully");

            if(count>0){
                loginMessageLabel.setText("Welcome!");
                MainPage(curUserName);
            }else{
                loginMessageLabel.setText("Incorrect username or password. Please try again!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void MainPage(String username){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("mainPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            stage.setTitle("COEN 275, Group 3, E-Commerce: "+username);
            stage.setScene(scene);
            stage.show();
            loginPane.getScene().getWindow().hide();
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


}
