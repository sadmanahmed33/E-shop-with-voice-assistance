package com.example.priya.E_ShopWithvoiceAssistance.Common;

public class Product {
    private String Name,Image,Description,Price,Discount,MenuId,Rating;
    public Product(){

    }


    public Product(String name, String image, String description, String menuId, String price, String discount, String rating){
        Name = name;
        Image = image;
        Description = description;
        MenuId = menuId;
        Price = price;

        Discount = discount;
        Rating = rating;

    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
