package com.moutamid.dantlicorp.Model;

public class UserModel {
    public String name, dob, email, phone_number, id, city, state;
    public String is_courier;
    public String image_url;
    public String key;
    public double lat, lng;

    public UserModel() {
    }

    public UserModel(String key, double lat, double lng, String image_url, String name) {
        this.key = key;
        this.name = name;
        this.image_url = image_url;
        this.lat = lat;
        this.lng = lng;
    }

    public UserModel(double lat, double lng, String image_url, String name) {
        this.name = name;
        this.image_url = image_url;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
