package com.example.coen275ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class ProductDetailPageController {
    @FXML
    private ImageView imgSource;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label price;
    @FXML
    private Label soldBy;
    @FXML
    private Label starRating;
    @FXML
    private VBox customersReviewContainer;
    private Product product;

    public void configureUI(Product product) {
        this.product = product;
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSource())));
        imgSource.setImage(image);
        title.setText(product.getTitle());
        price.setText("$" + String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        soldBy.setText("Sold By: " + product.getOwnBy());
        starRating.setText("Overall Rating: " + String.valueOf(product.getStarRating()) + " / 5.0");

        try {
            for (CustomerReview review : product.getCustomersTextReview()) {
                try {
                    FXMLLoader fxml = new FXMLLoader();
                    fxml.setLocation(getClass().getResource("customerReview.fxml"));
                    Parent reviewUI = fxml.load();
                    CustomerReviewController customerReviewController = fxml.getController();
                    customerReviewController.configureUI(review);
                    customersReviewContainer.getChildren().add(reviewUI);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML private void showAddReviewDialog() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.addEventHandler(WindowEvent.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (event.getEventType() == WindowEvent.WINDOW_HIDDEN){
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("mainPage.fxml"));
                        Stage mainStage = new Stage();
                        mainStage.setTitle("COEN 275, Group 3, E-Commerce");
                        Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
                        mainStage.setScene(scene);
                        mainStage.show();
                        title.getScene().getWindow().hide();
                    } catch (Exception e) {

                    }
                }
            }
        });
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("addReviewDialog.fxml"));
            Parent root = fxml.load();
            Scene dialogScene = new Scene(root,400,200);
            AddReviewDialogController addReviewDialogController = fxml.getController();
            addReviewDialogController.configure(dialog, LoginController.getPageName(), product.getId());
            dialog.setScene(dialogScene);
            dialog.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
