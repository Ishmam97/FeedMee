package com.ishmam.feedmee.Model;

public class Orders {
    private String pid, name, phone, address, price;

    public Orders(String pid, String name, String phone, String address, String price) {
        this.pid = pid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.price = price;
    }

    public Orders() {
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }


}



