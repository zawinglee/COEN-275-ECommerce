package com.example.coen275ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.Objects;

public class ProductController {
    @FXML
    private ImageView productImage;

    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label price;
    @FXML
    private Label quantity;

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
        title.setText(product.getTitle());
        price.setText("$" + String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        quantity.setText(String.valueOf(product.getQuantity()));
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
