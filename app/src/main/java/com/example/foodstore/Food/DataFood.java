package com.example.foodstore.Food;

public class DataFood {

    private int idFood;
    private String nameFood;
    private String imageFood;
    private int price;
    private int idCateOfFood;

    public DataFood(int idFood , String nameFood , String imageFood , int price , int idCateOfFood) {
        this.idFood = idFood;
        this.nameFood = nameFood;
        this.imageFood = imageFood;
        this.price = price;
        this.idCateOfFood = idCateOfFood;
    }

    public int getIdFood() {
        return idFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public String getImageFood() {
        return imageFood;
    }

    public int getPrice() {
        return price;
    }

    public int getIdCateOfFood() {
        return idCateOfFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIdCateOfFood(int idCateOfFood) {
        this.idCateOfFood = idCateOfFood;
    }
}
