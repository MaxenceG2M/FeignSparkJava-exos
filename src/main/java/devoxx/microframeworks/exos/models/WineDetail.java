package devoxx.microframeworks.exos.models;

import java.util.List;

public class WineDetail {
    private Wine wine;
    private Stock stock;
    private List<Comment> comments;

    @Override
    public String toString() {
        return String.format("WineDetail{stock=%s, wine=%s}", stock, wine);
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
