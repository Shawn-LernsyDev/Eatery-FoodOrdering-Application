package com.example.nirvanaeatery.Domain;

import java.io.Serializable;

public class UserDomain implements Serializable {

 //Create Variables
    private String firstName;
    private String lastName;
    private String email;
    private String PhoneNumber;
    private String ImageUrl;

//Create Constructor

    public UserDomain(){

    }

    public UserDomain(String firstName, String lastName, String email, String phoneNumber, String imageUrl){

        this.ImageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.PhoneNumber = phoneNumber;

    }

//Create getters and setters


    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
