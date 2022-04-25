package com.example.onlineclothesstore;

public class CustomerPurchases{

    public String title, manufacturer, category, image, price, itemID;

    public String getTitle() {
        return title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getItemID() {
        return itemID;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CustomerPurchases(){

    }

    public CustomerPurchases(String title, String manufacturer, String category, String image, String itemID, String price){
        this.title = title;
        this.manufacturer = manufacturer;
        this.category = category;
        this.image = image;
        this.itemID = itemID;
        this.price = price;
    }

}

