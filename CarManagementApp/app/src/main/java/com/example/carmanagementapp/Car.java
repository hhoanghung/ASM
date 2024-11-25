package com.example.carmanagementapp;

import java.io.Serializable;

public class Car implements Serializable {
    private String _id; // ID của xe
    private String name;
    private String manufacturer;
    private int year;
    private double price;
    private String description;

    // Constructor dùng khi thêm hoặc cập nhật xe
    public Car(String name, String manufacturer, int year, double price, String description) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.year = year;
        this.price = price;
        this.description = description;
    }

    // Getter và Setter (Retrofit cần Getter)
    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}


