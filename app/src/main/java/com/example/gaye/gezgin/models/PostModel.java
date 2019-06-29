package com.example.gaye.gezgin.models;

public class PostModel {

    int PostPicture;
    String PostName;
    String PostDescription;

    public PostModel() {

    }

    public PostModel(int PostPicture, String PostName, String PostDescription) {

        this.PostPicture = PostPicture;
        this.PostName = PostName;
        this.PostDescription = PostDescription;
    }

    public int getPostPicture() {
        return PostPicture;
    }

    public void setPostPicture(int postPicture) {
        PostPicture = postPicture;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    public String getPostDescription() {
        return PostDescription;
    }

    public void setPostDescription(String postDescription) {
        PostDescription = postDescription;
    }
}
