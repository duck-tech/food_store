package com.example.foodstore.ListOrder;

public class DataListOrder {
    private int id;
    private String nameTable;
    private String date;
    private int total;
    private int status;

    public DataListOrder(int id , String nameTable, String date, int total, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.date = date;
        this.total = total;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getNameTable() {
        return nameTable;
    }

    public String getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }

    public int getStatus() {
        return status;
    }
}
