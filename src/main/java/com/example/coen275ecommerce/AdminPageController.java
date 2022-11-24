package com.example.coen275ecommerce;

import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Button logoutButton, orderButton, imageButton;

    @FXML
    TextField productNameTextField;

    @FXML
    TextField priceTextField;

    @FXML
    TextField quantityTextField;

    @FXML
    TextArea descriptionTextField;

    @FXML
    ChoiceBox productTypeCheckBox;

    @FXML
    Label sellerPageMsg;

    @FXML
    TableView<Product> productTableView;

    @FXML
    TableColumn<Product, String> productName;

    @FXML
    TableColumn<Product, Integer> price;

    @FXML
    TableColumn<Product, Integer> quantity;

    @FXML
    TableColumn<Product, String> description;

    @FXML
    TableColumn<Product, String> productType;

    @FXML
    TableColumn<Product, String> imgSource;

    @FXML
    ImageView imageDisplay;

    private Image prodImage;

    private static ObservableList<Product> productList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }

    public ObservableList<Product> getProductsInSystem() {
        productList = FXCollections.observableArrayList();
        Connection connection = CreateDB.getConnection();
        String username = LoginController.getPageName();
        String query = "SELECT * FROM Product WHERE adminName = " + "'" + username + "' ;";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            Product product;
            while (resultSet.next()) {
                product = new Product();
                product.setTitle(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setOwnBy(resultSet.getString("adminName"));
                product.setDescription(resultSet.getString("description"));
                product.setProductType(resultSet.getString("productType"));
                product.setImageSource(resultSet.getString("image"));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void showProducts() {
        ObservableList<Product> productList = getProductsInSystem();

        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        productType.setCellValueFactory(new PropertyValueFactory<Product, String>("productType"));
        imgSource.setCellValueFactory(new PropertyValueFactory<Product, String>("imageSource"));

        productTableView.setItems(productList);

        productTableView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Product rowData = row.getItem();
                    productNameTextField.setText(rowData.getTitle());
                    priceTextField.setText(String.valueOf(rowData.getPrice()));
                    quantityTextField.setText(String.valueOf(rowData.getQuantity()));
                    descriptionTextField.setText(rowData.getDescription());
                    productTypeCheckBox.setValue(String.valueOf(rowData.getProductType()));
                    String imgUrl = "src/main/resources" + rowData.getImageSource();
                    File file = new File(imgUrl);
                    Image image = new Image(file.toURI().toString());
                    imageDisplay.setImage(image);
                }
            });
            return row;
        });
    }

    public void logoutButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("loginPage.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addProductAction(ActionEvent event) throws InterruptedException {
        String prodName = productNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();
        String description = descriptionTextField.getText();
        String productType = (String) productTypeCheckBox.getValue();
        if (prodName.length() == 0 || price.length() == 0 || quantity.length() == 0 || description.length() == 0
                || productType.length() == 0 || prodImage == null) {
            sellerPageMsg.setText("Failed to insert! Adding should fill out every information field.");
            return;
        }
        String imgDirType = productType;
        if (productType.equals("vehicles")) {
            imgDirType = "vehicle";
        }
        // image processing
        Image imageToBeSaved = imageDisplay.getImage();
        String fileName = "src/main/resources/img/" + imgDirType + "/" + prodName + ".jpg";
        String storeFileName = "/img/" + imgDirType + "/" + prodName + ".jpg";
        File outputFile = new File(fileName);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageToBeSaved, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InsertDB.insertProdToSystem(prodName, Integer.valueOf(price), Integer.valueOf(quantity), description, storeFileName,
                LoginController.getPageName(), productType);
        sellerPageMsg.setText("Successfully insert!");
        showProducts();
    }

    public void updateProductAction(ActionEvent event) {
        String prodName = productNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();
        String description = descriptionTextField.getText();
        String productType = (String) productTypeCheckBox.getValue();
        int prodCount = SelectDB.selectProdFromSystem(prodName, LoginController.getPageName());
        if (prodCount == 0) {
            sellerPageMsg.setText("This is not your product, please retry to edit again");
            return;
        }
        Product prod = SelectDB.selectProdResultFromSystem(prodName, LoginController.getPageName());
        if (price.length() != 0) {
            prod.setPrice(Integer.valueOf(price));
        }
        if (quantity.length() != 0) {
            prod.setQuantity(Integer.valueOf(quantity));
        }
        if (description.length() != 0) {
            prod.setDescription(description);
        }
        if (productType.length() != 0) {
            prod.setProductType(productType);
        }
        String imgDirType = productType;
        if (productType.equals("vehicles")) {
            imgDirType = "vehicle";
        }
        // image processing
        Image imageToBeSaved = imageDisplay.getImage();
        String fileName = "src/main/resources/img/" + imgDirType + "/" + prodName + ".jpg";
        String storeFileName = "/img/" + imgDirType + "/" + prodName + ".jpg";
        File outputFile = new File(fileName);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageToBeSaved, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(storeFileName);
        prod.setImageSource(storeFileName);
        UpdateDB.updateProdInSystem(prod);
        sellerPageMsg.setText("Update successfully!");
        showProducts();
    }

    public void deleteProductionAction(ActionEvent event) {
        String prodName = productNameTextField.getText();
        int prodCount = SelectDB.selectProdFromSystem(prodName, LoginController.getPageName());
        if (prodCount == 0) {
            sellerPageMsg.setText("This is not your product, please retry to delete again");
            return;
        }
        DeleteDB.deleteProdInSystem(prodName, LoginController.getPageName());
        sellerPageMsg.setText("Delete successfully!");
        productNameTextField.setText("");
        priceTextField.setText("");
        quantityTextField.setText("");
        descriptionTextField.setText("");
        String imgUrl = imageDisplay.getImage().getUrl();
        System.out.println(imgUrl);
        imageDisplay.setImage(null);
        File file = new File(imgUrl);
        file.delete();
        showProducts();
    }

    public void clearProductionAction(ActionEvent event) {
        productNameTextField.setText("");
        priceTextField.setText("");
        quantityTextField.setText("");
        descriptionTextField.setText("");
        imageDisplay.setImage(null);
    }

    public static ObservableList<Product> getProductList() {
        return productList;
    }

    public void ordersButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) orderButton.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("adminOrder.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("COEN 275, Group 3, E-Commerce");
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            loginStage.setScene(scene);
            loginStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void uploadImageAction(ActionEvent event) {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        if (file != null) {
            String imagepath = file.toURI().toString();
            ;
            prodImage = new Image(imagepath);
            imageDisplay.setImage(prodImage);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
            /*alert.setContentText("You didn't select a file!");*/
            alert.showAndWait();
        }
    }
}
