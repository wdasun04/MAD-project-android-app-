package com.example.firebasetest;

public class Upload {
    private String itemCode;
    private String itemName;
    private String price;
    private String size;
    private String colour;
    private String description;
    private String imageUrl;

    public Upload() {

     }

    public Upload(String itemCode, String itemName, String price, String size, String colour, String description, String imageUrl) {


        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.size = size;
        this.colour = colour;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getItemCode() {

        return itemCode;
    }

    public void setItemCode(String itemCode) {

        this.itemCode = itemCode;
    }

    public String getItemName() {

        return itemName;
    }

    public void setItemName(String itemName) {

        this.itemName = itemName;
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

        this.price = price;
    }

    public String getSize() {

        return size;
    }

    public void setSize(String size) {

        this.size = size;
    }

    public String getColour() {

        return colour;
    }

    public void setColour(String colour) {

        this.colour = colour;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }


}
