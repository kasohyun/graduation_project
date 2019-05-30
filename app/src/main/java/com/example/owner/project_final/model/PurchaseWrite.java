package com.example.owner.project_final.model;

import java.io.Serializable;

public class PurchaseWrite implements Serializable {
    private String addedByUser;
    private String title;
    private String writer;

    private String editTradeDate;
    private String editTradeTime;
    private  String editCost;
    private String tradeItem;

    private String address;
    private String detailAddress;

    private String photoID;

    private String description;

    private String latitude;
    private String longitude;

    private String postingDate;

    private String id;

    public PurchaseWrite() {
    }

    public PurchaseWrite(String addedByUser, String title, String writer, String editTradeDate, String editTradeTime, String editCost, String tradeItem,
                         String address, String detailAddress, String photoID, String description, String latitude, String longitude, String postingDate, String id) {
        this.addedByUser = addedByUser; //FirebaseApi.getCurrentUser().getUid()
        this.title = title; //editTItle
        this.writer = writer;   //editUser
        this.editTradeDate = editTradeDate; //editTradeDate
        this.editTradeTime = editTradeTime; //editTradeTime
        this.editCost = editCost;   //editCost
        this.tradeItem = tradeItem; //tradeItem
        this.address = address; //editAddress
        this.detailAddress = detailAddress; //editDetailAddress
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

    public String geteditCost() {
        return editCost;
    }

    public String getTradeItem() {
        return tradeItem;
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
        return "PurchaseWrite{" +
                "addedByUser='" + addedByUser + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", editTradeDate='" + editTradeDate + '\'' +
                ", editTradeTime='" + editTradeTime + '\'' +
                ", tradeItem='" + tradeItem + '\'' +
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
