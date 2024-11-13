package com.nagarro.training.corejavatraining.models;

public class Product {
    // Fields
    private int count = 0; // Static variable to keep track of item count
    private String id;
    private String color;
    private String size;
    private String brand;
    private double price; // Optional price field
    private String type; 

    // Constructor
    public Product(String id, String brand, String color, String size, double price, String type) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.price = price;
        this.type = type;
        
    }

    // Constructor for files with fewer columns
    public Product(String id, String brand, String color, String size, String type) {
        this(id, brand, color, size, 0.0, type); // Default price to 0
        
    }

    public String getId() { return id; }
    
    public String getColor() { return color; }

    public String getSize() { return size; }

    public String getBrand() { return brand; }

    public double getPrice() { return price; }
    
    public String getType(){ return type; }
     

    
    // Override toString() for easy display of product information
    @Override
    public String toString() {

        return "Item {"+
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
