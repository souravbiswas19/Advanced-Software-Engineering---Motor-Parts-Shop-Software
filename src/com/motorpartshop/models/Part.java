package com.motorpartshop.models;

public class Part {

    private int id;
    private String name;
    private String description;
    private int stock;
    private int reorderThreshold;
    private String rackLocation;

    public Part(int id, String name, String description, int stock, int reorderThreshold, String rackLocation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.reorderThreshold = reorderThreshold;
        this.rackLocation = rackLocation;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(int reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public String getRackLocation() {
        return rackLocation;
    }

    public void setRackLocation(String rackLocation) {
        this.rackLocation = rackLocation;
    }
}
