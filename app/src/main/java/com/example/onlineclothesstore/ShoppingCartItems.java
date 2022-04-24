package com.example.onlineclothesstore;

public class ShoppingCartItems{

    public String title, manufacturer, category, image, price, quantity, itemID, shoppingCartItemID;

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

    public String getShoppingCartItemID() {
        return shoppingCartItemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setShoppingCartItemID(String shoppingCartItemID) {
        this.shoppingCartItemID= shoppingCartItemID;
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

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ShoppingCartItems(){

    }

    public ShoppingCartItems(String title, String manufacturer, String category, String image, String itemID, String price, String quantity, String shoppingCartItemID){
        this.title = title;
        this.manufacturer = manufacturer;
        this.category = category;
        this.image = image;
        this.itemID = itemID;
        this.price = price;
        this.quantity = quantity;
        this.shoppingCartItemID = shoppingCartItemID;
    }

}

