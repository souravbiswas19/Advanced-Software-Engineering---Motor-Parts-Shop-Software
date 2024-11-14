package com.motorpartshop.models;

import java.util.Date;

public class Sales {
    private int id;
    private Part part;
    private int quantity;
    private double totalAmount;
    private Date salesDate;

    public Sales(int id, Part part, int quantity, double totalAmount, Date salesDate) {
        this.id = id;
        this.part = part;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.salesDate = salesDate;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }
}
