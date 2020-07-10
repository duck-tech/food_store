package com.example.foodstore.CartShopping;

public class DataCartShopping {

    private int idFood;
    private String imageFood;
    private String nameFood;
    private int price;
    private int soluong;

    public DataCartShopping(int idFood, String imageFood , String nameFood , int price, int soluong) {
        this.idFood = idFood;
        this.imageFood = imageFood;
        this.nameFood = nameFood;
        this.price = price;
        this.soluong = soluong;
    }

    public String getImageFood() {
        return imageFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getIdFood() {
        return idFood;
    }

    public int getPrice() {
        return price;
    }

}
