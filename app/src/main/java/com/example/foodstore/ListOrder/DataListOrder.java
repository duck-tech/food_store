package com.example.foodstore.ListOrder;

public class DataListOrder {
    private String nameTable;
    private String date;
    private int total;
    private int status;

    public DataListOrder(String nameTable, String date, int total, int status) {
        this.nameTable = nameTable;
        this.date = date;
        this.total = total;
        this.status = status;
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
