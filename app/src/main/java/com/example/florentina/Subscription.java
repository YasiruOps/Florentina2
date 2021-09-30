package com.example.florentina;

import com.google.firebase.firestore.Exclude;

public class Subscription {

    private String Name, Desription, ImageURL;
    private Float Price;

    @Exclude
    private String subid;



    Subscription(){}

    public Subscription(String name, String desription, String imageURL, Float price) {
        Name = name;
        Desription = desription;
        ImageURL = imageURL;
        Price = price;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
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
