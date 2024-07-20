package com.example.android_final_project.Model;



public abstract class BusinessActivity {
    private String id = "";
    private String description = "";
    private String date = ""; //date format: "dd/mm/yy"
    private Double price = 0.0D;


    public BusinessActivity() {

    }

    public String getDescription() {
        return description;
    }

    public BusinessActivity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDate() {
        return date;
    }

    public BusinessActivity setDate(String date) {
        this.date = date;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public BusinessActivity setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getId() {
        return id;
    }

    public BusinessActivity setId(String id) {
        this.id = id;
        return this;
    }
}
