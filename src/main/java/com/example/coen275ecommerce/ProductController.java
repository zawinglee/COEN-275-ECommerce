package com.example.coen275ecommerce;

import javafx.event.ActionEvent;
import db.CreateDB;
import db.InsertDB;
import db.SelectDB;
import db.UpdateDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.util.Objects;

public class ProductController {
    @FXML
    private ImageView productImage;

    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel, quantityLabel;
    @FXML
    private Button addToCartButton;
    int count;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void configureUI(Product product) {
        setProduct(product);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSource())));
        productImage.setImage(image);
        nameLabel.setText(product.getTitle());
        priceLabel.setText("$" + String.valueOf(product.getPrice()));
        descriptionLabel.setText(product.getDescription());
        ProductInCart prodInCart = SelectDB.selectProdFromCart(LoginController.getPageName(), product.getTitle());
        count = prodInCart == null ? 0 : prodInCart.getQuantity();
        quantityLabel.setText(count+"");
    }

    public void addToCartButtonOnAction(ActionEvent event) {
        count++;
        quantityLabel.setText(count+"");
        String prod_name = nameLabel.getText();
        String description = descriptionLabel.getText();
        String priceString = priceLabel.getText();

        int signIndex = priceString.indexOf("$");
        int price = Integer.parseInt(priceString.substring(signIndex+1));
        int quantity = Integer.parseInt(quantityLabel.getText());
        int totalPrice = price * quantity;
        String totalPriceString = "$"+totalPrice;
        ProductInCart product = new ProductInCart(prod_name, description, priceString, quantity, totalPriceString);
        String curUsername = LoginController.getPageName();
        if(count == 1) {
            InsertDB.insertProdToCart(curUsername, product);
        }else if(count >= 2){
            UpdateDB.updateProdInCart(curUsername, product);
        }

    }

    @FXML private void showProductDetailPage() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("productDetailPage.fxml"));
            Parent root = fxml.load();
            ProductDetailPageController detailPageController = fxml.getController();
            detailPageController.configureUI(getProduct());
            Scene dialogScene = new Scene(root,1300, 700);
            dialog.setScene(dialogScene);
            dialog.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
