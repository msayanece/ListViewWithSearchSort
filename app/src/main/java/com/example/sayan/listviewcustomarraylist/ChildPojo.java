package com.example.sayan.listviewcustomarraylist;

/**
 * Created by Sayan on 12-Nov-17.
 */

//Prerequisite
// Model object of each child view resource
public class ChildPojo {
    // Has two properties one is text and another is image resource id
    private String text;
    private int Image;

    //constructor for creating object with resources
    public ChildPojo(int image, String text){
        this.Image = image;
        this.text = text;
    }

    //to get the image resource id, call it
    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    //to get the text, call it
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
