package com.example.priya.E_ShopWithvoiceAssistance;
public class Registration_Class {

    private String name;
    private String address;
    private String mobile;
    private String password;
    private String reEnterPassword;

    public Registration_Class() {
    }

    public Registration_Class(String name, String address, String password, String reEnterPassword, String mobile) {
        this.name = name;
        this.address = address;
        this.password = password;
        this.reEnterPassword = reEnterPassword;
        this.mobile = mobile;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
