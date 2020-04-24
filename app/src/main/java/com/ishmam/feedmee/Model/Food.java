package com.ishmam.feedmee.Model;

public class Food {
    private String name;
    private String price;
    private String description;
    private String imgUrl;
    private String id;

    public Food(){

    }



    public Food(String name, String price, String description, String imgUrl , String id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
