package com.ishmam.feedmee.Model;

public class Cart {
    private String pid,pname,price,discount ;
    private int quantity;

    public Cart(){
    }


    public Cart(String pid, String pname, String price, int quantity) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
