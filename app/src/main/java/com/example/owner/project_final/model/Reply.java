package com.example.owner.project_final.model;

import java.util.HashMap;
import java.util.Map;

public class Reply {
    private String addedByUser;
    private boolean anonymous = false;
    private String name;
    private String date;
    private String content;
    private String id;

    public Reply() {
    }

    public Reply(String addedByUser, boolean anonymous, String name, String date, String content, String id) {
        this.addedByUser = addedByUser;
        this.anonymous = anonymous;
        this.name = name;
        this.date = date;
        this.content = content;
        this.id = id;
    }

    public String getAddedByUser() {
        return addedByUser;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "addedByUser='" + addedByUser + '\'' +
                ", anonymous=" + anonymous +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
