package com.example.blocknote.adapter;

import android.net.Uri;

import java.io.Serializable;

public class Note implements Serializable {
    private int id = 0;
    private String title;
    private String description;
    private String uri = "empty";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
