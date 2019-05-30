package com.example.owner.project_final.model;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String addedByUser;
    //[오투잡]처음에 글을쓰면 답글이 없으므로 생성자에는 추가하지 않는다.
    private Map<String, Reply> replyMap = new HashMap<>();
    private boolean anonymous = false;
    private String name;
    private String date;
    private String content;
    private String nodeId;
    private String childId;
    private boolean isReply = false;


    public Comment() {
    }

    public Comment(String addedByUser, boolean anonymous, String name, String date, String content, String nodeId, String childId,boolean isReply) {
        this.addedByUser = addedByUser;
        this.anonymous = anonymous;
        this.name = name;
        this.date = date;
        this.content = content;
        this.nodeId = nodeId;
        this.childId = childId;
        this.isReply = isReply;
    }

    public String getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(String addedByUser) {
        this.addedByUser = addedByUser;
    }

    public Map<String, Reply> getReplyMap() {
        return replyMap;
    }

    public void setReplyMap(Map<String, Reply> replyMap) {
        this.replyMap = replyMap;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "addedByUser='" + addedByUser + '\'' +
                ", replyMap=" + replyMap +
                ", anonymous=" + anonymous +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", childId='" + childId + '\'' +
                ", isReply=" + isReply +
                '}';
    }
}
