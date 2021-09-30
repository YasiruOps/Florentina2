package com.example.florentina;

import com.google.firebase.firestore.Exclude;

public class Product {
    private String Name, Description, ImageURL;
    private Float Price;
    private Integer Quantity;



    @Exclude
    private String productId;

    public Product() {
    }

    public Product(String name, String description, String imageURL, Float price, Integer quantity) {
        Name = name;
        Description = description;
        ImageURL = imageURL;
        Price = price;
        Quantity = quantity;
    }


    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }
}
