package com.example.florentina;

public class Subscription {

    private String Name, Desription, ImageURL;
    private Float Price;

    Subscription(){}

    public Subscription(String name, String desription, String imageURL, Float price) {
        Name = name;
        Desription = desription;
        ImageURL = imageURL;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesription() {
        return Desription;
    }

    public void setDesription(String desription) {
        Desription = desription;
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
