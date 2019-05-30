package com.example.owner.project_final.model;

import java.io.Serializable;

public class RoomWrite implements Serializable {
    private String addedByUser;
    private String title;
    private String writer;

    private String rentalStartTime;
    private String rentalEndTime;
    private String rentalFee;

    private String roomOption1;
    private String roomOption2;
    private String roomOption3;
    private String roomOption4;
    private String roomOption5;

    private String roomLimitOption1;
    private String roomLimitOption2;
    private String roomLimitOption3;
    private String roomLimitOption4;

    private String address;
    private String detailAddress;

    private String photoID;

    private String description;

    private String latitude;
    private String longitude;

    private String postingDate;

    private String id;

    public RoomWrite() {
    }

    public RoomWrite(String addedByUser, String title, String writer, String rentalStartTime, String rentalEndTime, String rentalFee,
                     String roomOption1, String roomOption2, String roomOption3, String roomOption4, String roomOption5,
                     String roomLimitOption1, String roomLimitOption2, String roomLimitOption3, String roomLimitOption4,
                     String address, String detailAddress, String photoID, String description, String latitude, String longitude, String postingDate, String id) {
        this.addedByUser = addedByUser;
        this.title = title;
        this.writer = writer;
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.rentalFee = rentalFee;
        this.roomOption1 = roomOption1;
        this.roomOption2 = roomOption2;
        this.roomOption3 = roomOption3;
        this.roomOption4 = roomOption4;
        this.roomOption5 = roomOption5;
        this.roomLimitOption1 = roomLimitOption1;
        this.roomLimitOption2 = roomLimitOption2;
        this.roomLimitOption3 = roomLimitOption3;
        this.roomLimitOption4 = roomLimitOption4;
        this.address = address;
        this.detailAddress = detailAddress;
        this.photoID = photoID;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postingDate = postingDate;
        this.id = id;
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

    public String getRentalStartTime() {
        return rentalStartTime;
    }

    public String getRentalEndTime() {
        return rentalEndTime;
    }

    public String getRentalFee() {
        return rentalFee;
    }

    public String getRoomOption1() {
        return roomOption1;
    }

    public String getRoomOption2() {
        return roomOption2;
    }

    public String getRoomOption3() {
        return roomOption3;
    }

    public String getRoomOption4() {
        return roomOption4;
    }

    public String getRoomOption5() {
        return roomOption5;
    }

    public String getRoomLimitOption1() {
        return roomLimitOption1;
    }

    public String getRoomLimitOption2() {
        return roomLimitOption2;
    }

    public String getRoomLimitOption3() {
        return roomLimitOption3;
    }

    public String getRoomLimitOption4() {
        return roomLimitOption4;
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
        return "RoomWrite{" +
                "addedByUser='" + addedByUser + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", rentalStartTime='" + rentalStartTime + '\'' +
                ", rentalEndTime='" + rentalEndTime + '\'' +
                ", rentalFee='" + rentalFee + '\'' +
                ", roomOption1='" + roomOption1 + '\'' +
                ", roomOption2='" + roomOption2 + '\'' +
                ", roomOption3='" + roomOption3 + '\'' +
                ", roomOption4='" + roomOption4 + '\'' +
                ", roomOption5='" + roomOption5 + '\'' +
                ", roomLimitOption1='" + roomLimitOption1 + '\'' +
                ", roomLimitOption2='" + roomLimitOption2 + '\'' +
                ", roomLimitOption3='" + roomLimitOption3 + '\'' +
                ", roomLimitOption4='" + roomLimitOption4 + '\'' +
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
