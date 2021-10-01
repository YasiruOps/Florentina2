package com.example.florentina;

import com.google.firebase.firestore.Exclude;

public class Event {
    private String name, desc, imgurl, expdate;
    private Float price;

    @Exclude
    private String eventId;

    public Event(){}

    public Event(String name, String desc, String imgurl, String expdate, Float price) {
        this.name = name;
        this.desc = desc;
        this.imgurl = imgurl;
        this.expdate = expdate;
        this.price = price;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
