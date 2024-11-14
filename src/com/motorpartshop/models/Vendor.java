package com.motorpartshop.models;

import java.util.List;

public class Vendor {

    private int id;
    private String name;
    private String address;
    private String contact;
    private List<Part> suppliedParts;

    public Vendor(int id, String name, String address, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Part> getSuppliedParts() {
        return suppliedParts;
    }

    public void setSuppliedParts(List<Part> suppliedParts) {
        this.suppliedParts = suppliedParts;
    }
}
