package devoxx.microframeworks.exos.models;

public class CellarEntry {

    private Wine wine;
    private int quantity;
    private boolean favorite;

    @Override
    public String toString() {
        return String.format("CellarEntry{wine=%s, quantity=%d, favorite=%s}", wine, quantity, favorite);
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
