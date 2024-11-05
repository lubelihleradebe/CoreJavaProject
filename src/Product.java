public class Product {
    // Fields
    private String id;
    private String color;
    private String size;
    private String brand;
    private double price; // Optional price field
    private String type; 

    // Constructor
    public Product(String brand, String color, String size, double price, String type) {
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.price = price;
        this.type = type;
    }

    // Constructor for files with fewer columns
    public Product(String brand, String color, String size, String type) {
        this(brand, color, size, 0.0, type); // Default price to 0
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    // Getters and Setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setType(String Type){
        this.type = type;
    }
    public String getType(){
        return type;
    }


    // Override toString() for easy display of product information
    @Override
    public String toString() {
        return "Product{"+
                "color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
