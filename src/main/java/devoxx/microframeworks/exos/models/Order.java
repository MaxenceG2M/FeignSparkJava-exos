package devoxx.microframeworks.exos.models;

import java.util.List;

public class Order {
    private List<OrderEntry> entries;

    @Override
    public String toString() {
        return String.format("Order{entries=%s}", entries);
    }

    public List<OrderEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntry> entries) {
        this.entries = entries;
    }
}
