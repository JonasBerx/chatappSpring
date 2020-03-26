package com.chat.chatapp.db;

public class Comments {
    private String user;
    private String comment;
    private int rating;


    public Comments(String user, String de, int rating) {
        this.rating = rating;
        this.user = user;
        this.comment = de;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
