package com.example.florentina;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Customer  implements Serializable {



    @Exclude private String id;



    String Address, Email, Name, Password, Phone, UserName;

    public Customer(){}

    public Customer(String address, String email, String name, String password, String phone, String userName) {
        Address = address;
        Email = email;
        Name = name;
        Password = password;
        Phone = phone;
        UserName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
