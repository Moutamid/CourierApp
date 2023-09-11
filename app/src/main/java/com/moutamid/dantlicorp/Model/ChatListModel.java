package com.moutamid.dantlicorp.Model;

public class ChatListModel {
    String ID, image, name, message, chat_id;
    long timeStamp;

    public ChatListModel() {
    }

    public ChatListModel(String ID, String image, String name, String message, long timeStamp) {
        this.ID = ID;
        this.image = image;
        this.name = name;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }
}
