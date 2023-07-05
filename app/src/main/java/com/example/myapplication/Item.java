package com.example.myapplication;

public class Item {
    private int id;
    private String description;
    private String composition;
    private String manufacturer;

    private String brand;

    private String country;
    private String weight;
    private String volume;
    private String code;

    public Item(String description, String composition, String manufacturer, String brand,
                String country, String weight, String volume, String code) {
        this.description = description;
        this.composition = composition;
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.country = country;
        this.weight = weight;
        this.volume = volume;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
