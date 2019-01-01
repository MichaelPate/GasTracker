package com.example.michael.gastracker;

public class logEntryClass {
    private int entryId = 0;
    private String date = "mm/dd/yyyy";
    private String distance = "0.0";
    private String price = "0.00";
    private String volume = "0.000";
    private String cost = "0.00";
    private String memo = "null";

    public logEntryClass(int eId) {
        this.entryId = eId;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public void setId(int id) {
        this.entryId = id;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMemo() {
        return this.memo;
    }
    public int getId() {
        return this.entryId;
    }
    public String getDate() {
        return this.date;
    }
    public String getDistance() {
        return this.distance;
    }
    public String getPrice() {
        return this.price;
    }
    public String getVolume() {
        return this.volume;
    }
    public String getCost() {
        return this.cost;
    }
}
