package com.chat.chatapp.model;

public class Comment {
    private String name;
    private String descr;
    private int rating;

    public Comment(String name, String descr, int rating) {
        setName(name);
        setDescr(descr);
        setRating(rating);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
