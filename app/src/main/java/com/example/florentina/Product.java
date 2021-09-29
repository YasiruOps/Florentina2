package com.example.florentina;

public class Product {
    private String Name, Description, ImageURL;
    private Float Price;



    public Product() {
    }

    public Product(String name, String description, String imageURL, Float price) {
        Name = name;
        Description = description;
        ImageURL = imageURL;
        Price = price;
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
