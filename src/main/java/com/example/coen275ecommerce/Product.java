package com.example.coen275ecommerce;

import java.util.ArrayList;

public class Product {
    private String imageSource;
    private String title;
    private int price;
    private String description;
    private int id;
    private int quantity;
    private double starRating;
    private String ownBy;
    private String productType;
    private ArrayList<CustomerReview> customersTextReview = new ArrayList<>();

    public ArrayList<CustomerReview> getCustomersTextReview() {
        return customersTextReview;
    }

    public void setCustomersTextReview(ArrayList<CustomerReview> customersTextReview) {
        this.customersTextReview = customersTextReview;
    }

    public void addCustomerReview(CustomerReview review) {
        try {
            this.customersTextReview.add(review);
            starRating = Math.round((starRating + review.numericRating) / (customersTextReview.size() + 1) * 10) / 10D;
        } catch (Exception e) {

        }
    }

    public String getOwnBy() {
        return ownBy;
    }

    public void setOwnBy(String ownBy) {
        this.ownBy = ownBy;
    }

    public double getStarRating() {
        return starRating;
    }

    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
}