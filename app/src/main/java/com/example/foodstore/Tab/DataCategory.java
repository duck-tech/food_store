package com.example.foodstore.Tab;

public class DataCategory {

    private int id;
    private String nameCate;

    public DataCategory(int id , String nameCate) {
        this.id = id;
        this.nameCate = nameCate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }

    public int getId() {
        return id;
    }

    public String getNameCate() {
        return nameCate;
    }
}
