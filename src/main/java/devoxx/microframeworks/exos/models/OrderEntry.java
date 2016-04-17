package devoxx.microframeworks.exos.models;

public class OrderEntry {
    private String wid;
    private int quantity;

    @Override
    public String toString() {
        return String.format("OrderEntry{wid='%s', quantity=%d}", wid, quantity);
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
