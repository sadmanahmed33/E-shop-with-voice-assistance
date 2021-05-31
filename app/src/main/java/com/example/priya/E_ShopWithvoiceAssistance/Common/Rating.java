package com.example.priya.E_ShopWithvoiceAssistance.Common;

public class Rating {
    private String userPhone;
    private String productId;
    private String rateValue;
    private String Comment;

    public Rating()
    {

    }
    public Rating(String userPhone, String productId, String rateValue, String comment) {
        this.userPhone = userPhone;
        this.productId = productId;
        this.rateValue = rateValue;
        Comment = comment;

    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }


}
