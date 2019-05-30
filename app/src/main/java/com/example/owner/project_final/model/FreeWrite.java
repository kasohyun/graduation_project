package com.example.owner.project_final.model;

import java.io.Serializable;

public class FreeWrite implements Serializable {
    private String addedByUser;
    private String title;
    private String writer;

    private boolean unknown_name;

    private String description;

    private String postingDate;

    private String id;

    public FreeWrite() {
    }
/*    사진 삽입
    public FreeWrite(String addedByUser, String title, String writer,  boolean unknown_name, String photoID, String description, String postingDate, String id) {
        this.addedByUser = addedByUser; //FirebaseApi.getCurrentUser().getUid()
        this.title = title; //editTItle
        this.writer = writer;   //editUser
        this.unknown_name = unknown_name; //unknown_name
        this.photoID = photoID; //storagekey
        this.description = description; //editDescription
        this.postingDate = postingDate; //postingDate
        this.id = id;   //autokey
    }
*/
    public FreeWrite(String addedByUser, String title, String writer,  boolean unknown_name, String description, String postingDate, String id) {
        this.addedByUser = addedByUser; //FirebaseApi.getCurrentUser().getUid()
        this.title = title; //editTItle
        this.writer = writer;   //editUser
        this.unknown_name = unknown_name; //unknown_name
        this.description = description; //editDescription
        this.postingDate = postingDate; //postingDate
        this.id = id;
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

        if (this.getUnknownName()==true) {  //익명 체크 시 글쓴이 이름 대신 '익명' 출력
            return "익명";
        } else {
            return writer;
        }
    }

    public boolean getUnknownName() {
        return unknown_name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "FreeWrite{" +
                "addedByUser='" + addedByUser + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", unKnownCheck='" + unknown_name + '\'' +
                ", description='" + description + '\'' +
                ", postingDate='" + postingDate + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
