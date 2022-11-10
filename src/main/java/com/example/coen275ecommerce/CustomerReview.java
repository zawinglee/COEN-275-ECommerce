package com.example.coen275ecommerce;

public class CustomerReview {
    String authorUsername;
    double numericRating;
    String textRating;
    int productID;

    public CustomerReview(String authorUsername, double numericRating, String textRating, int productID) {
        this.authorUsername = authorUsername;
        this.numericRating = numericRating;
        this.textRating = textRating;
        this.productID = productID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public double getNumericRating() {
        return numericRating;
    }

    public void setNumericRating(double numericRating) {
        this.numericRating = numericRating;
    }

    public String getTextRating() {
        return textRating;
    }

    public void setTextRating(String textRating) {
        this.textRating = textRating;
    }
}
