package com.example.owner.project_final.model;

import java.io.Serializable;

public class HobbyWrite implements Serializable {
    private String addedByUser;
    private String title;
    private String writer;

    private int option_hobby_result_count;

    private String editTradeDate;
    private String editTradeTime;
    private  String editCost;

    private String option_hobby_result;
    private String option_hobby_01;
    private String option_hobby_02;
    private String option_hobby_03;
    private String option_hobby_04;

    private String address;
    private String detailAddress;

    private String photoID;

    private String description;

    private String latitude;
    private String longitude;

    private String postingDate;

    private String id;

    public HobbyWrite() {
    }

    public HobbyWrite(String addedByUser, String title, String writer, String editTradeDate, String editTradeTime, String editCost, int option_hobby_result_count,
                      String option_hobby_result, String option_hobby_01, String option_hobby_02, String option_hobby_03, String option_hobby_04,
                      String address, String detailAddress, String photoID, String description, String latitude, String longitude, String postingDate, String id) {
        this.addedByUser = addedByUser; //FirebaseApi.getCurrentUser().getUid()
        this.title = title; //editTItle
        this.writer = writer;   //editUser
        this.option_hobby_result_count = option_hobby_result_count; //option_hobby_result_count
        this.editTradeDate = editTradeDate; //editTradeDate
        this.editTradeTime = editTradeTime; //editTradeTime
        this.editCost = editCost;   //editCost
        this.option_hobby_result = option_hobby_result;  //String option_hobby_result
        this.option_hobby_01 = option_hobby_01;  //String option_hobby_01
        this.option_hobby_02 = option_hobby_02;  //String option_hobby_02
        this.option_hobby_03 = option_hobby_03;  //String option_hobby_03
        this.option_hobby_04 = option_hobby_04;  //String option_hobby_04
        this.address = address; //editAddress
        this.detailAddress = detailAddress; //editHobbyDetailAddress
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

    public int getoption_hobby_result_count() {
        return option_hobby_result_count;
    }

    public String geteditTradeDate() {
        return editTradeDate;
    }

    public String geteditTradeTime() {
        return editTradeTime;
    }

    public String geteditCost() {
        return editCost;
    }

    public String getoption_hobby_result() {
        return option_hobby_result;
    }

    public String getoption_hobby_01() {
        return option_hobby_01;
    }

    public String getoption_hobby_02() {
        return option_hobby_02;
    }

    public String getoption_hobby_03() {
        return option_hobby_03;
    }

    public String getoption_hobby_04() {
        return option_hobby_04;
    }

    public String getAddress() {
        return address;
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
        return "HobbyWrite{" +
                "addedByUser='" + addedByUser + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", editTradeDate='" + editTradeDate + '\'' +
                ", editTradeTime='" + editTradeTime + '\'' +
                ", option_hobby_result='" + option_hobby_result + '\'' +
                ", address='" + address + '\'' +
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
