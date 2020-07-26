package com.example.foodstore.ListOrder;

public class DataListDialog {
    private String image;
    private String name;
    private int price;
    private int amount;

    public DataListDialog(String image, String name, int price, int amount) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
