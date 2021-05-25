package com.example.firebasetest;

public class Comment {

    private String comment;
    private String suggesion;

    public Comment(String comment, String suggesion) {
        this.comment = comment;
        this.suggesion = suggesion;
    }

    public Comment() {

    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment;
    }

    public String getSuggesion() {

        return suggesion;
    }

    public void setSuggesion(String suggesion) {

        this.suggesion = suggesion;
    }
}

