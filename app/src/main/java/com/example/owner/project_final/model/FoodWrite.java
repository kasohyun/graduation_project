package com.example.owner.project_final.model;

import java.io.Serializable;

public class FoodWrite implements Serializable {
    private String addedByUser;
    private String title;
    private String writer;

    private String editTradeDate;
    private String editTradeTime;

    private String option_food_pay_result;
    private String option_food_pay_01;
    private String option_food_pay_02;
    private String option_food_divide_result;
    private String option_food_divide_01;
    private String option_food_divide_02;

    private String foodaddress;
    private String detailAddress;

    private String photoID;

    private String description;

    private String latitude;
    private String longitude;

    private String postingDate;

    private String id;

    public FoodWrite() {
    }

    public FoodWrite(String addedByUser, String title, String writer, String editTradeDate, String editTradeTime,
                     String option_food_pay_result, String option_food_divide_result,
                     String foodaddress, String detailAddress, String photoID, String description, String latitude, String longitude, String postingDate, String id) {
        this.addedByUser = addedByUser; //FirebaseApi.getCurrentUser().getUid()
        this.title = title; //editTItle
        this.writer = writer;   //editUser
        this.editTradeDate = editTradeDate; //editTradeDate
        this.editTradeTime = editTradeTime; //editTradeTime
        this.option_food_pay_result = option_food_pay_result; //option_food_pay_result
        this.option_food_divide_result = option_food_divide_result;   //option_food_divide_result
        this.foodaddress = foodaddress; //editFoodAddress
        this.detailAddress = detailAddress; //editFoodDetailAddress
        this.photoID = photoID; //storagekey
        this.description = description; //editDescription
        this.latitude = latitude;   //latitude
        this.longitude = longitude; //longitude
        this.postingDate = postingDate; //postingDate
        this.id = id;   //autokey
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public String getId() {
        return id;
    }

    public String getAddedByUser() {
        return addedByUser;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String geteditTradeDate() {
        return editTradeDate;
    }

    public String geteditTradeTime() {
        return editTradeTime;
    }

    public String getoption_food_pay_result() {
        return option_food_pay_result;
    }

    public String getoption_food_pay_01() {
        return option_food_pay_01;
    }

    public String getoption_food_pay_02() {
        return option_food_pay_02;
    }

    public String getoption_food_divide_result() {
        return option_food_divide_result;
    }

    public String getoption_food_divide_01() {
        return option_food_divide_01;
    }

    public String getoption_food_divide_02() {
        return option_food_divide_02;
    }

    public String getAddress() {
        return foodaddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getPhotoID() {
        return photoID;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "FoodWrite{" +
                "addedByUser='" + addedByUser + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", editTradeDate='" + editTradeDate + '\'' +
                ", editTradeTime='" + editTradeTime + '\'' +
                ", option_food_pay_result='" + option_food_pay_result + '\'' +
                ", option_food_divide_result='" + option_food_divide_result + '\'' +
                ", foodaddress='" + foodaddress + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", photoID='" + photoID + '\'' +
                ", description='" + description + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", postingDate='" + postingDate + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
