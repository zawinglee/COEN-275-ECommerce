package com.example.coen275ecommerce.controller;

import com.example.coen275ecommerce.entity.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML private AnchorPane mainPageAnchor;
    @FXML private AnchorPane leftPanel;

    @FXML private Button electronBtn, menBtn, womenBtn;

    @FXML private AnchorPane electronPanel, menPanel, womenPanel;
    @FXML private VBox electronVBox;
    @FXML private GridPane electronGridPane;

    private ArrayList<Product> electronics;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        electronics = new ArrayList<Product>(generate_electronics());
        try{
            for (Product elec : electronics) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("product.fxml"));
                electronVBox = fxml.load();
                ProductController productController = fxml.getController();
                productController.configure(elec);

                electronGridPane.add(electronVBox, 0, 0);
                GridPane.setMargin(electronVBox, new Insets(10));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in HoodieM");
        }
    }

    private ArrayList<Product> generate_electronics() {
        ArrayList<Product> res = new ArrayList<>();

        Product product = new Product();

        product.setImageSource("/img/electronic/iphone14pro.png");
        product.setTitle("Apple iPhone 14 Pro");
        product.setPrice(1299);
        product.setDescription("the latest iPhone!");

        res.add(product);
        return res;
    }

    @FXML
    private void onCategorySelected(ActionEvent event) {
        if (event.getSource() == electronBtn) {
            electronPanel.setVisible(true);
            menPanel.setVisible(false);
            womenPanel.setVisible(false);
        } else if (event.getSource() == menBtn) {
            electronPanel.setVisible(false);
            menPanel.setVisible(true);
            womenPanel.setVisible(false);
        } else if (event.getSource() == womenBtn) {
            electronPanel.setVisible(false);
            menPanel.setVisible(false);
            womenPanel.setVisible(true);
        }
    }
}
