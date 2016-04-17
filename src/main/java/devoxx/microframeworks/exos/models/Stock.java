package devoxx.microframeworks.exos.models;

public class Stock {
    private int stock;
    private double price;

    @Override
    public String toString() {
        return String.format("Stock{stock=%d, price=%s}", stock, price);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
