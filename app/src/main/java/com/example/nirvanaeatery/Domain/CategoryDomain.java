//This Domain is what is mapped to the Category List carrying details about the items
package com.example.nirvanaeatery.Domain;

import android.os.Parcelable;

import java.io.Serializable;

public class CategoryDomain implements Serializable {

//Creating Variables
    private String title;
    private String picture;

// Generating constructors

    public CategoryDomain(String title, String picture) {
        this.title = title;
        this.picture = picture;
    }

    //Alt + Insert to create Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) { this.picture = picture;
    }
}
