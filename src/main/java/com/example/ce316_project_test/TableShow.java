package com.example.ce316_project_test;

import javafx.scene.image.ImageView;

public class TableShow {

    private int id;
    private String title;
    private ImageView image2;

    public TableShow(int id, String title, ImageView image2) {
        this.id = id;
        this.title = title;
        this.image2 = image2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageView getImage() {
        return image2;
    }

    public void setImage(ImageView image2) {
        this.image2 = image2;
    }
}
