package com.example.cashbook2;

public class Cash {
    private int id; // 添加一个id成员变量
    private String name;
    private String date;
    private String type;
    private String amount;
    private String description;

    public Cash(int id, String name, String date, String type, String amount, String description) {
        this.id = id; // 在构造函数中初始化id
        this.name = name;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public int getId() { // 添加一个getId方法
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
